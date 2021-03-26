package domain.expression;

import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.type.BoolType;
import domain.type.IType;
import domain.type.IntType;
import domain.value.BoolValue;
import domain.value.IValue;
import exceptions.ExpressionException;
import exceptions.TypeException;

public class LogicExpression implements IExpression
{
    private IExpression expression1;
    private IExpression expression2;
    private String operator;

    public LogicExpression(String operator,IExpression expression1,IExpression expression2)
    {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ExpressionException
    {
        IValue value1,value2;
        value1 = this.expression1.evaluate(table,heap);
        if(value1.getType().equals(new BoolType()))
        {
            value2 = this.expression2.evaluate(table,heap);
            if(value2.getType().equals(new BoolType()))
            {
                BoolValue bool1 = (BoolValue)value1;
                BoolValue bool2 = (BoolValue)value2;
                boolean left,right;
                left = bool1.GetValue();
                right = bool2.GetValue();
                switch (this.operator)
                {
                    case "&&": return new BoolValue(left&&right);
                    case "||": return new BoolValue(left||right);
                    default: throw  new ExpressionException("invalid operator");
                }
            }
            else
                throw new ExpressionException("second operator is not bool type");
        }
        else
            throw  new ExpressionException("first operator is not bool type");
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws TypeException
    {
        IType typeE1 = expression1.typecheck(typeEnv);
        IType typeE2 = expression2.typecheck(typeEnv);
        if(typeE1.equals(new BoolType()))
        {
            if(typeE2.equals(new BoolType()))
                return new BoolType();
            else
                throw new TypeException(this.expression2.toString()+" is not of type boolean");
        }
        else
            throw new TypeException(this.expression1.toString()+" is not of type boolean");

    }

    public String toString()
    {
        return "LogicExpression{ e1= " + this.expression1.toString() + " e2= " + this.expression2.toString() + " op= " + this.operator + "}";
    }
}
