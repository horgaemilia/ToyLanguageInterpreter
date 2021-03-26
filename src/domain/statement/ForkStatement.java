package domain.statement;

import domain.ProgramState;
import domain.adts.*;
import domain.type.IType;
import domain.value.IValue;
import domain.value.StringValue;
import exceptions.StatementException;
import exceptions.TypeException;

import java.io.BufferedReader;

public class ForkStatement implements IStatement{

    private IStatement statement;

    public ForkStatement(IStatement statement)
    {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException
    {
        IStack<IStatement> stack = new MyStack<IStatement>();
        IDictionary<String, IValue> symTable = state.getSymTable().cloneDictionary();
        IHeap heap = state.getHeap();
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IList<IValue> output = state.getOut();
        return new ProgramState(stack,symTable,output,fileTable,statement,heap);
    }

    public String toString()
    {
        return "fork( " + this.statement.toString() + " )";
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        return statement.typecheck(typeEnv.cloneDictionary());
    }
}
