package domain.expression;

import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.type.IType;
import domain.value.IValue;
import exceptions.DictionaryException;
import exceptions.ExpressionException;
import exceptions.TypeException;

public class VariableExpression implements IExpression{
    private String id;

    public VariableExpression(String id)
    {
        this.id = id;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ExpressionException {
        try{
            return table.GetValue(this.id);
        }
        catch (DictionaryException ex)
        {
            throw new ExpressionException("cannot find " + this.id);
        }
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        return typeEnv.GetValue(id);
    }

    public String getId()
    {
        return this.id;
    }

    public String toString()
    {
        return this.id;
    }
}
