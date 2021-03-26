package domain.statement;

import domain.ProgramState;
import domain.adts.IBarrierTable;
import domain.adts.IDictionary;
import domain.adts.IStack;
import domain.type.IType;
import domain.value.IValue;
import domain.value.IntValue;
import exceptions.StatementException;
import exceptions.TypeException;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStatementBarrier implements IStatement{

    private String variable_name;
    private static Lock lock = new ReentrantLock();

    public AwaitStatementBarrier(String variable_name) {
        this.variable_name = variable_name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        lock.lock();
        IBarrierTable barrier = state.getBarrierTable();
        IDictionary<String, IValue> symbolTable = state.getSymTable();
        IStack<IStatement> executionStack = state.getExeStack();
        if(!symbolTable.ExistsKey(this.variable_name))
            throw new StatementException("igen");
        IValue value = symbolTable.GetValue(this.variable_name);
        Integer index = ((IntValue)value).GetValue();
        if(!barrier.ExistsKey(index))
            throw new StatementException("igen");
        Pair<Integer, List<Integer>> barriervalue = barrier.GetValue(index);
        List<Integer> mylist = barriervalue.getValue();
        Integer length = mylist.size();
        if(barriervalue.getKey() > length)
        {
            if(mylist.contains(state.GetId()))
                executionStack.push(this);
            else
            {
                mylist.add(state.GetId());
                executionStack.push(this);
            }
        }
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        return null;
    }
}
