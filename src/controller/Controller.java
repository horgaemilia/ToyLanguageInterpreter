package controller;

import Observer.MyObservable;
import domain.ProgramState;
import domain.adts.*;
import domain.statement.IStatement;
import domain.type.IType;
import domain.value.IValue;
import domain.value.RefValue;
import exceptions.MyExecutionException;
import exceptions.StackException;
import exceptions.TypeException;
import repository.IRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Controller extends MyObservable {
    private IRepository repo;
    private boolean displayFlag;
    private ExecutorService executor;


    public Controller(IRepository repo)
    {

        this.repo = repo;
        this.displayFlag = false;
    }

    public void SetProgramList(List<ProgramState> programStates)
    {
        this.repo.SetProgramList(programStates);
    }


    public void SetExecutor(ExecutorService executor)
    {
        this.executor = executor;
    }

    public ExecutorService GetExecutor()
    {
        return this.executor;
    }

    public void AddProgram(ProgramState state)
    {
        this.repo.AddProgram(state);
    }

    private Map<Integer,IValue> GarbageCollector(List<Integer> symTableAddr,Map<Integer,IValue> heap)
    {
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTableAndHeap(Collection<IValue> symTableValues,Collection<IValue> heapValues){
         return Stream.concat(
                symTableValues.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> {RefValue v1 = (RefValue)v; return v1.getAddress();}),
                heapValues.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> {RefValue v1 = (RefValue)v; return v1.getAddress();})
        ).collect(Collectors.toList());

    }

    public List<ProgramState> RemoveCompletedProgram(List<ProgramState> ProgramStates)
    {
        return ProgramStates.stream()
                .filter(p->p.isNotCompleted())
                .collect(Collectors.toList());
    }


    public void OneStepForAllPrograms(List<ProgramState> programStates) throws InterruptedException, MyExecutionException
    {
        programStates.forEach(prg->repo.logProgramStateExecution(prg));
        List<Callable<ProgramState>> callList = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> {
                    if(this.displayFlag)
                        System.out.println(p.toString());
                return p.oneStep();}))
                .collect(Collectors.toList());
        List<ProgramState> newPrgList = executor.invokeAll(callList). stream()
                . map(future -> { try { return future.get();}
                catch(InterruptedException e )
                {
                    System.out.println(e.getMessage());
                    return null;
                }
                catch (ExecutionException e)
                {
                    throw new MyExecutionException(e.getMessage());
                }
                }
                )
                .filter(p -> p!=null)
                .collect(Collectors.toList());
        programStates.addAll(newPrgList);
        programStates.forEach(prg ->repo.logProgramStateExecution(prg));
        repo.SetProgramList(programStates);
        Notify();
    }

    public void AllSteps() throws RuntimeException
    {
        executor = Executors.newFixedThreadPool(2);
//remove the completed programs
        List<ProgramState> prgList= RemoveCompletedProgram(repo.getProgramList().RetrieveAll());
        while(prgList.size() > 0){
            try {
                prgList.forEach(programState -> programState.getHeap().SetContent(GarbageCollector(getAddrFromSymTableAndHeap(programState.getSymTable().GetContent().values(),
                        programState.getHeap().GetContent().values()),
                        programState.getHeap().GetContent())));
                OneStepForAllPrograms(prgList);

//remove the completed programs
                prgList = RemoveCompletedProgram(repo.getProgramList().RetrieveAll());
            }
            catch (InterruptedException ex)
            {

            }
        }
        executor.shutdownNow();
//HERE the repository still contains at least one Completed Prg
// update the repository state
        repo.SetProgramList(prgList);
    }

    public MyList<ProgramState> GetPrograms()
    {
        return this.repo.getProgramList();
    }

    public ProgramState getProgramById(Integer id)
    {
        ArrayList<ProgramState> programs = this.repo.getProgramList().RetrieveAll();
        int i;
        for(i=0;i<programs.size();i++)
        {
            if(programs.get(i).GetId() == id)
                return programs.get(i);
        }
        return null;
    }

    public void GarbageCollectorSafe(List<ProgramState> programStates)
    {
        programStates.forEach(programState -> programState.getHeap().SetContent(GarbageCollector(getAddrFromSymTableAndHeap(programState.getSymTable().GetContent().values(),
                programState.getHeap().GetContent().values()),
                programState.getHeap().GetContent())));
        Notify();
    }

}
