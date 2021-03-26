package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.ILatchTable;
import domain.adts.IStack;
import domain.expression.ValueExpression;
import domain.type.IType;
import domain.value.IValue;
import domain.value.IntValue;
import exceptions.StatementException;
import exceptions.TypeException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDown implements IStatement{


    private String variable_name;
    private static Lock lock = new ReentrantLock();

    public CountDown(String variable_name) {
        this.variable_name = variable_name;
    }


    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        lock.lock();
        IStack<IStatement> stack = state.getExeStack();
        IDictionary<String, IValue> symTable = state.getSymTable();
        ILatchTable latchTable = state.getLatchTable();
        if(!symTable.ExistsKey(this.variable_name))
            throw new StatementException("nem");
        Integer index = ((IntValue)symTable.GetValue(variable_name)).GetValue();
        if(!latchTable.ExistsKey(index))
            throw new StatementException("nem");
        Integer value = latchTable.GetValue(index);
        if(value>0)
            latchTable.put(index,value-1);
        stack.push(new PrintStatement(new ValueExpression(new IntValue(state.GetId()))));
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        return null;
    }
}
