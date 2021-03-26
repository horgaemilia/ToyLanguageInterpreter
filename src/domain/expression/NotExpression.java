package domain.expression;

import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.type.BoolType;
import domain.type.IType;
import domain.value.BoolValue;
import domain.value.IValue;
import exceptions.ExpressionException;
import exceptions.TypeException;

public class NotExpression  implements IExpression{
    private IExpression expression1;

    public NotExpression(IExpression expression1) {
        this.expression1 = expression1;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ExpressionException {
        IValue value = this.expression1.evaluate(table,heap);
        if(value.equals(new BoolValue(true)))
            return new BoolValue(false);
        else
            return new BoolValue(true);
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws TypeException
    {
        IType typeE1 = expression1.typecheck(typeEnv);
        if(typeE1.equals(new BoolType()))
        {
            return new BoolType();
        }
        else
            throw new TypeException(this.expression1.toString()+" is not of type boolean");
    }

    @Override
    public String toString() {
        return "!" + this.expression1.toString();
    }
}
