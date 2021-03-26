package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.adts.IStack;
import domain.expression.IExpression;
import domain.type.IType;
import domain.value.IValue;
import exceptions.StatementException;
import exceptions.TypeException;

public class SwitchStatement implements IStatement{
    private IExpression myExpression;
    private IExpression expression1;
    private IExpression expression2;
    private IStatement statement1;
    private IStatement statement2;
    private IStatement default_stmt;

    public SwitchStatement(IExpression myExpression, IExpression expression1, IExpression expression2, IStatement statement1, IStatement statement2, IStatement default_stmt) {
        this.myExpression = myExpression;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.statement1 = statement1;
        this.statement2 = statement2;
        this.default_stmt = default_stmt;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<IStatement> stack = state.getExeStack();
        IDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        IValue value1 = this.expression1.evaluate(symTable,heap);
        IValue value2 = this.expression2.evaluate(symTable,heap);
        IValue valueExpr = this.myExpression.evaluate(symTable,heap);
        if(valueExpr.equals(value1))
        {
            stack.push(statement1);
            return null;
        }
        if(valueExpr.equals(value2))
        {
            stack.push(statement2);
            return null;
        }
        stack.push(default_stmt);
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        this.statement1.typecheck(typeEnv);
        this.statement2.typecheck(typeEnv);
        this.default_stmt.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "SwitchStatement{" +
                myExpression.toString() + "\n"+
                "(" + this.expression1.toString() + ")"
                + this.statement1.toString() + "\n"+
                "(" + this.expression2.toString() + ")"
                + this.statement2.toString() + "\n"+
                "default:" + this.default_stmt.toString()+
                '}';
    }
}
