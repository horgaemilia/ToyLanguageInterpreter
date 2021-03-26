package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.adts.IList;
import domain.expression.IExpression;
import domain.type.IType;
import domain.value.IValue;
import exceptions.ExpressionException;
import exceptions.StatementException;
import exceptions.TypeException;

public class PrintStatement implements IStatement{
    private IExpression expression;


    public PrintStatement(IExpression expression)
    {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IHeap heap = state.getHeap();
        IList<IValue> out = state.getOut();
        out.append(this.expression.evaluate(state.getSymTable(),heap));
        System.out.println("---------> output is " + this.expression.evaluate(state.getSymTable(),heap) + "\n");
        return null;

    }

    public String toString()
    {
        return "print( " + this.expression.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        this.expression.typecheck(typeEnv);
        return typeEnv;
    }
}
