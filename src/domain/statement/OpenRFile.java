package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.adts.IStack;
import domain.expression.IExpression;
import domain.type.IType;
import domain.type.StringType;
import domain.value.IValue;
import domain.value.StringValue;
import exceptions.StatementException;
import exceptions.TypeException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStatement
{
    private IExpression expression;

    public OpenRFile(IExpression expression)
    {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<IStatement> stack = state.getExeStack();
        IDictionary<String, IValue> symTable = state.getSymTable();
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap heap = state.getHeap();
        IValue value = this.expression.evaluate(symTable,heap);
        if(value.getType().equals(new StringType()))
        {
            StringValue stringValue = (StringValue)value;
            if(fileTable.ExistsKey(stringValue))
                throw new StatementException("file already opened");
            String filename = stringValue.getValue();
            try
            {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                fileTable.put(stringValue,reader);
            }
            catch (FileNotFoundException ex)
            {
                throw new StatementException("file cannot be opened");
            }
        }
        else
            throw new StatementException("expression is not of type string");
        return null;
    }
    public String toString()
    {
        return "open file: " + this.expression.toString();
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        IType typeExpression = this.expression.typecheck(typeEnv);
        if(typeExpression.equals(new StringType()))
            return typeEnv;
        else
            throw new TypeException("the type of " + this.expression.toString() + " is not a string type");
    }
}
