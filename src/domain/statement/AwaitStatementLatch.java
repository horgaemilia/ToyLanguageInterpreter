package domain.statement;

import domain.ProgramState;
import domain.adts.*;
import domain.type.IType;
import domain.value.IValue;
import domain.value.IntValue;
import exceptions.StatementException;
import exceptions.TypeException;

public class AwaitStatementLatch implements IStatement{

    private String variable_name;

    public AwaitStatementLatch(String variable_name) {
        this.variable_name = variable_name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<IStatement> stack = state.getExeStack();
        IDictionary<String, IValue> symTable = state.getSymTable();
        ILatchTable latchTable = state.getLatchTable();
        if(!symTable.ExistsKey(this.variable_name))
            throw new StatementException("nem");
        Integer index = ((IntValue)symTable.GetValue(variable_name)).GetValue();
        if(!latchTable.ExistsKey(index))
            throw new StatementException("nem");
        Integer value = latchTable.GetValue(index);
        if(value!=0)
            stack.push(this);
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        return null;
    }
}
