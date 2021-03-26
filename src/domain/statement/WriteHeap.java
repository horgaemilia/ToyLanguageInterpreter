package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.expression.IExpression;
import domain.type.IType;
import domain.type.RefType;
import domain.value.IValue;
import domain.value.RefValue;
import exceptions.StatementException;
import exceptions.TypeException;

public class WriteHeap implements IStatement{
    private String variable_name;
    private IExpression expression;

    public WriteHeap(String variable_name,IExpression expression)
    {
        this.variable_name = variable_name;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException
    {
        IDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        IValue expressionValue = expression.evaluate(symTable,heap);
        if(symTable.ExistsKey(variable_name))
        {
            if(symTable.GetValue(variable_name) instanceof RefValue)
            {
                int address = ((RefValue) symTable.GetValue(variable_name)).GetAddress();
                if(heap.ExistsKey(address))
                {
                    IType innerType = ((RefValue) symTable.GetValue(variable_name)).getInnerType();
                    if(expressionValue.getType().equals(innerType))
                    {
                        heap.put(address,expressionValue);
                    }
                    else
                        throw new StatementException("the types are not compatible");

                }
                else
                    throw new StatementException(address + " is not a key in the heap");
            }
            else
                throw new StatementException(this.variable_name + " is not associated to a refValue");

        }
        else
            throw new StatementException(this.variable_name + " is not declared in the symTable");
        return null;
    }

    public String toString()
    {
        return "WriteHeap{" + this.variable_name + ","+this.expression.toString() + "}";
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        if(typeEnv.ExistsKey(variable_name))
        {
            IType variableType = typeEnv.GetValue(variable_name);
            IType expressionType = this.expression.typecheck(typeEnv);
            if(variableType.equals(new RefType(expressionType)))
                return typeEnv;
            else
                throw new TypeException("the types do not match");

        }
        else
            throw new TypeException("the variable does not exist");
    }
}
