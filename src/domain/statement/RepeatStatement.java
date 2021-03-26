package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.IStack;
import domain.expression.IExpression;
import domain.expression.NotExpression;
import domain.type.BoolType;
import domain.type.IType;
import exceptions.StatementException;
import exceptions.TypeException;

public class RepeatStatement implements IStatement {

    private IStatement statement;
    private IExpression condition;

    public RepeatStatement(IExpression condition,IStatement statement) {
        this.statement = statement;
        this.condition = condition;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException
    {
        IStack<IStatement> stack = state.getExeStack();
        IExpression condition = new NotExpression(this.condition);
        IStatement while_stmt = new WhileStatement(condition,this.statement);
        IStatement compound  = new CompoundStatement(this.statement,while_stmt);
        stack.push(compound);
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        IType expressionType = this.condition.typecheck(typeEnv);
        if(expressionType.equals(new BoolType()))
        {
            this.statement.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new TypeException("the type of the expression is not boolean");
    }

    @Override
    public String toString() {
        return "Repeat" + statement.toString() +
                "until" + condition.toString() +
                '}';
    }
}
