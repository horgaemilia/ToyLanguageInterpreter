package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.ILock;
import domain.type.IType;
import domain.value.IValue;
import domain.value.IntValue;
import exceptions.StatementException;
import exceptions.TypeException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class NewLockStatement implements IStatement{

    private String variable_name;
    private static Lock lock = new ReentrantLock();

    public NewLockStatement(String variable_name)
    {
        this.variable_name = variable_name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException
    {
        lock.lock();
        ILock lockTable = state.getLock();
        IDictionary<String, IValue> symbolTable = state.getSymTable();

        Integer location = state.getLockAddress();

        lockTable.put(location, -1);
        symbolTable.put(this.variable_name,new IntValue(location));
        state.setSymTable(symbolTable);
        state.setLock(lockTable);
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        return typeEnv;
    }
}
