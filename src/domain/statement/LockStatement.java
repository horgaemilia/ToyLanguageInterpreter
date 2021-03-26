package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.ILock;
import domain.adts.IStack;
import domain.type.IType;
import domain.type.IntType;
import domain.value.IValue;
import domain.value.IntValue;
import exceptions.StatementException;
import exceptions.TypeException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStatement implements IStatement {

    private String variable_name;
    private static Lock lock = new ReentrantLock();

    public LockStatement(String variable_name)
    {
        this.variable_name = variable_name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        lock.lock();
        IStack<IStatement> stack = state.getExeStack();
        ILock lockTable = state.getLock();
        IDictionary<String, IValue> symbolTable = state.getSymTable();
        if(!symbolTable.ExistsKey(variable_name))
                throw new StatementException("variable is not declared");
        IValue value = symbolTable.GetValue(variable_name);
        if(!value.getType().equals(new IntType()))
            throw new StatementException("not an int type");
        IntValue valueInt = (IntValue)value;
        Integer index = valueInt.GetValue();
        if(!lockTable.ExistsKey(index))
            throw new StatementException("no such element in lock table");
        if(lockTable.GetValue(index) == -1)
            lockTable.put(index,state.GetId());
        else
            stack.push(this);
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        return null;
    }
}
