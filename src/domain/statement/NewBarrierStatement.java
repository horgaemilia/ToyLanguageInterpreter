package domain.statement;

import domain.ProgramState;
import domain.adts.IBarrierTable;
import domain.adts.IDictionary;
import domain.adts.ILock;
import domain.expression.IExpression;
import domain.type.IType;
import domain.type.IntType;
import domain.value.IValue;
import domain.value.IntValue;
import exceptions.StatementException;
import exceptions.TypeException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrierStatement implements IStatement {

    private String var;
    private IExpression expression;
    private static Lock lock = new ReentrantLock();

    @Override
    public ProgramState execute(ProgramState state) throws StatementException
    {
        lock.lock();
        IBarrierTable barrier = state.getBarrierTable();
        IDictionary<String, IValue> symbolTable = state.getSymTable();
        IValue value = this.expression.evaluate(symbolTable,state.getHeap());
        if(!value.getType().equals(new IntType()))
            throw new StatementException("nu nu");
        Integer number = ((IntValue)value).GetValue();
        Integer index = state.getBarrierAddress();
        barrier.put(index,new Pair<>(number,new ArrayList<>()));
        symbolTable.put(this.var,new IntValue(index));
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        return null;
    }

    @Override
    public String toString() {
        return "NewBarrierStatement{" +
                "var='" + var  +
                ", expression=" + expression.toString() +
                '}';
    }
}
