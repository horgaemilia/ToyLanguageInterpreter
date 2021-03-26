package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.adts.ILatchTable;
import domain.expression.IExpression;
import domain.type.IType;
import domain.type.IntType;
import domain.value.IValue;
import domain.value.IntValue;
import exceptions.StatementException;
import exceptions.TypeException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatch implements IStatement{


    private String var;
    private IExpression expression;
    private static Lock lock = new ReentrantLock();

    public NewLatch(String var, IExpression expression) {
        this.var = var;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        lock.lock();
        IDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        ILatchTable latchTable =  state.getLatchTable();
        IValue value = this.expression.evaluate(symTable,heap);
        if(!value.getType().equals(new IntType()))
            throw new StatementException("nem");
        Integer number = ((IntValue)value).GetValue();
        Integer index = latchTable.allocate(number);
        if(!symTable.ExistsKey(this.var))
            throw new StatementException("nem");
        else
            symTable.put(var,new IntValue(index));
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        return null;
    }
}
