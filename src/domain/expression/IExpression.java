package domain.expression;

import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.type.IType;
import domain.value.IValue;
import exceptions.ExpressionException;
import exceptions.TypeException;

public interface IExpression {
    IValue evaluate(IDictionary<String,IValue> table, IHeap heap) throws ExpressionException;
    IType typecheck(IDictionary<String,IType> typeEnv) throws TypeException;
}
