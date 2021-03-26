package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.adts.IStack;
import domain.expression.IExpression;
import domain.type.BoolType;
import domain.type.IType;
import domain.value.BoolValue;
import domain.value.IValue;
import exceptions.ExpressionException;
import exceptions.StatementException;
import exceptions.TypeException;

public class IfStatement implements IStatement{
    private IExpression expression;
    private IStatement thenStmt;
    private IStatement elseStmt;

    public IfStatement(IExpression expression,IStatement thenStmt,IStatement elseStmt) {
        this.expression = expression;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException
    {
        IStack<IStatement> stack = state.getExeStack();
        IDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        IValue value;
            value = this.expression.evaluate(symTable,heap);
            BoolValue conditionValue = (BoolValue)value;
            if(conditionValue.GetValue())
                stack.push(this.thenStmt);
            else
            {
                if(value.equals(new BoolValue(false)))
                    stack.push(this.elseStmt);
                else
                    throw new StatementException("the if statement cannot be evaluated");
            }

        return null;
    }
    public String toString()
    {
        return "(IF(" + expression.toString() + ") THEN (" + thenStmt.toString()+") ELSE (" + elseStmt.toString()+"))";
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        IType typeExpression = this.expression.typecheck(typeEnv);
        if(typeExpression.equals(new BoolType()))
        {
            this.thenStmt.typecheck(typeEnv.cloneDictionary());
            this.elseStmt.typecheck(typeEnv.cloneDictionary());
            return typeEnv;
        }
        else
            throw new TypeException(this.expression.toString() + " is not of bool type");
    }
}
