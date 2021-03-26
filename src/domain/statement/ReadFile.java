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

public class ReadFile implements IStatement {
    private IExpression expression;
    private String var_name;

    public ReadFile(IExpression expression, String var_name)
    {
        this.expression = expression;
        this.var_name = var_name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException
    {
        IStack<IStatement> stack = state.getExeStack();
        IHeap heap = state.getHeap();
        IDictionary<String, IValue> symTable = state.getSymTable();
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IValue value = this.expression.evaluate(symTable,heap);
        if(symTable.ExistsKey(var_name))
        {
            IValue varValue = symTable.GetValue(var_name);
            if(varValue.getType().equals(new IntType()))
            {
                if(value.getType().equals(new StringType()))
                {
                    if(fileTable.ExistsKey((StringValue) value))
                    {
                        BufferedReader reader = fileTable.GetValue((StringValue) value);
                        try
                        {
                           String read =  reader.readLine();
                           if(read == null)
                               symTable.replace(var_name,new IntValue(0));
                           else
                               try
                               {
                                   int intValue = Integer.parseInt(read);
                                   symTable.replace(var_name,new IntValue(intValue));
                               }
                               catch (NumberFormatException e)
                               {
                                   throw new StatementException("please have numbers in the file");
                               }
                        }
                        catch (IOException ex)
                        {
                            throw new StatementException("io exception ");
                        }
                    }
                    else throw new StatementException("the file does not exist");
                }
                else throw new StatementException("it is not of type string");
            }
            else throw new StatementException("the type of the variable must be an int");
        }
        else
            throw new StatementException("the variable does not exist");
        return null;
    }

    public String toString()
    {
        return this.var_name + " is read from " + this.expression.toString();
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException
    {
        if(typeEnv.ExistsKey(var_name))
        {
            IType variableType = typeEnv.GetValue(var_name);
            IType expressionType = this.expression.typecheck(typeEnv);
            if(variableType.equals(new IntType()))
            {
                if(expressionType.equals(new StringType()))
                    return typeEnv;
                else
                    throw new TypeException("the type of the expression is not string");
            }
            else
                throw new TypeException("the type of the variable is not int");
        }
        else throw new TypeException("the variable is not declared");
    }
}
