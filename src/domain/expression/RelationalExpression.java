package domain.expression;

import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.type.BoolType;
import domain.type.IType;
import domain.type.IntType;
import domain.value.BoolValue;
import domain.value.IValue;
import domain.value.IntValue;
import exceptions.ExpressionException;
import exceptions.TypeException;

public class RelationalExpression implements IExpression{
    private IExpression expression1;
    private IExpression expression2;
    private String operator;

    public RelationalExpression( String operator,IExpression expression1, IExpression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }



    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws ExpressionException
    {
        IValue value1,value2;
        value1 = this.expression1.evaluate(table,heap);
        if(value1.getType().equals(new IntType()))
        {
            value2 = this.expression2.evaluate(table,heap);
            if(value2.getType().equals(new IntType()))
            {
                IntValue int1 = (IntValue)value1;
                IntValue int2 = (IntValue)value2;
                int left_number,right_number;
                left_number = int1.GetValue();
                right_number = int2.GetValue();
                switch (this.operator)
                {
                    case "<": return new BoolValue(left_number<right_number);
                    case ">": return new BoolValue(left_number>right_number);
                    case "==": return new BoolValue(left_number==right_number);
                    case "!=": return new BoolValue(left_number!=right_number);
                    case ">=": return new BoolValue(left_number>=right_number);
                    case "<=": return new BoolValue(left_number<=right_number);
                    default: throw new ExpressionException("this operator does not exist");
                }
            }
            else throw new ExpressionException("second operator is not of type int");
        }
        else throw new ExpressionException("first operator is not of type int");
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        IType typeE1 = expression1.typecheck(typeEnv);
        IType typeE2 = expression2.typecheck(typeEnv);
        if(typeE1.equals(new IntType()))
        {
            if(typeE2.equals(new IntType()))
                return new BoolType();
            else
                throw new TypeException(this.expression2.toString()+" is not of type int");
        }
        else
            throw new TypeException(this.expression1.toString()+" is not of type int");
    }


    public String toString()
    {
        return this.expression1.toString() + this.operator + this.expression2.toString();
    }
}
