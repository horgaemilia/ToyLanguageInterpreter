package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.adts.IStack;
import domain.expression.IExpression;
import domain.type.IType;
import domain.type.IntType;
import domain.type.StringType;
import domain.value.IValue;
import domain.value.IntValue;
import domain.value.StringValue;
import exceptions.StatementException;
import exceptions.TypeException;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile  implements IStatement{
    private IExpression expression;

    public CloseRFile(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException
    {
        IDictionary<String, IValue> symTable = state.getSymTable();
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap heap = state.getHeap();
        IValue value = this.expression.evaluate(symTable,heap);
        if(value.getType().equals(new StringType()))
        {
            StringValue key = (StringValue)value;
            if(fileTable.ExistsKey(key))
            {
                BufferedReader reader = fileTable.GetValue(key);
                try
                {
                    reader.close();
                    fileTable.delete(key);
                }
                catch (IOException ex)
                {
                    throw  new StatementException("io exception happened");
                }
            }
            else throw new StatementException("the key does not exist");
        }
        else throw new StatementException("the expression does not evaluate to a string value");
        return null;
    }
    public String toString()
    {
        return "close file " + this.expression.toString();
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
