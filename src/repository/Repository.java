package repository;

import domain.ProgramState;
import domain.adts.MyList;
import domain.adts.MyStack;
import exceptions.LogException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Repository implements IRepository{

    private MyList<ProgramState> states;
    private String LogFilePath;

    public Repository(String filePath)
    {
        this.LogFilePath = filePath;
        try
        {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(this.LogFilePath)),true);
            writer.close();
        }
        catch (IOException ex)
        {

        }
        this.states = new MyList<ProgramState>();
    }


    @Override
    public void AddProgram(ProgramState newProgram) {
        this.states.append(newProgram);
    }

    @Override
    public void logProgramStateExecution(ProgramState state) throws LogException
    {
        try
        {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(this.LogFilePath,true)));
            writer.println("Id: " + state.GetId());
            writer.println("Execution Stack: ");
            writer.println(((MyStack)state.getExeStack()).myToString());
            writer.println("");

            writer.println("Symbol table: " + state.GetId());
            writer.println(state.getSymTable().toString());
            writer.println("");

            writer.println("Heap Table: ");
            writer.println(state.getHeap().toString());
            writer.println("");

            writer.println("Output: ");
            writer.println(state.getOut().toString());
            writer.println("");

            writer.println("FileTable: ");
            writer.println(state.getFileTable().toString());
            writer.println("");


            writer.println("lockTable: ");
           // writer.println(state.getLock().toString());
            writer.println(".........................................");
            writer.close();
        }
        catch (IOException ex)
        {
            throw new LogException(" io exception happened with the log file");
        }
    }

    @Override
    public MyList<ProgramState> getProgramList() {
        return this.states;
    }

    @Override
    public void SetProgramList(List<ProgramState> programStates)
    {
        this.states.SetContent(programStates);
    }
}
