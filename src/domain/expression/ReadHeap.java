package domain.expression;

import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.type.IType;
import domain.type.RefType;
import domain.value.IValue;
import domain.value.RefValue;
import exceptions.ExpressionException;
import exceptions.StatementException;
import exceptions.TypeException;

public class ReadHeap implements IExpression
{
    private IExpression expression;

    public ReadHeap(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ExpressionException {
        IValue expressionValue = this.expression.evaluate(table,heap);
        if(expressionValue instanceof RefValue)
        {
            int address = ((RefValue) expressionValue).GetAddress();
            IValue referencedValue = heap.GetValue(address);
            if(referencedValue == null)
                throw  new ExpressionException("we could not find a value at the adress " + address);
            return referencedValue;
        }
        else
            throw new StatementException(this.expression.toString() + " is not evaluated to a ref value");
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        IType typeE = this.expression.typecheck(typeEnv);
        if(typeE instanceof RefType)
        {
            RefType refTypeE = (RefType)typeE;
            return refTypeE.getInner();
        }
        else
            throw new TypeException(this.expression.toString() + " is not a ref type");
    }

    public String toString()
    {
        return "ReadHeap {" + this.expression.toString() + "}";
    }
}
