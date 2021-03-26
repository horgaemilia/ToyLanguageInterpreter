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
import exceptions.StatementException;
import exceptions.TypeException;

public class WhileStatement implements IStatement
{
    private IExpression expression;
    private IStatement statement;

    public WhileStatement(IExpression expression,IStatement statement)
    {
        this.expression = expression;
        this.statement = statement;
    }


    @Override
    public ProgramState execute(ProgramState state) throws StatementException
    {

        IDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        IStack<IStatement> stack = state.getExeStack();
        IValue expressionValue = expression.evaluate(symTable,heap);
        if(expressionValue.getType().equals(new BoolType()))
        {
            boolean result = ((BoolValue)expressionValue).GetValue();
            if(result)
            {
                stack.push(new WhileStatement(expression,statement));
                stack.push(statement);
            }
        }
        else
            throw new StatementException(" the expression does not evaluate to a bool type");

        return null;
    }
    public String toString()
    {
        return "While( " + this.expression.toString() + ")  { " +  this.statement.toString() + "} ";
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        IType expressionType = this.expression.typecheck(typeEnv);
        if(expressionType.equals(new BoolType()))
        {
            this.statement.typecheck(typeEnv);
            return typeEnv;
        }
        else
            throw new TypeException("the type of the expression is not boolean");

    }
}
