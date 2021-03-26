package domain.expression;

import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.type.IType;
import domain.value.IValue;
import exceptions.ExpressionException;
import exceptions.TypeException;

public class ValueExpression implements IExpression {

    IValue value;
    public ValueExpression(IValue value)
    {
        this.value = value;
    }
    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ExpressionException {
        return this.value;
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        return value.getType();
    }

    public String toString()
    {
        return this.value.toString();
    }
}
