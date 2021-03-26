package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.IStack;
import domain.expression.*;
import domain.type.IType;
import exceptions.StatementException;
import exceptions.TypeException;

public class ForStatement implements IStatement{

    private String variable_name;
    private IExpression initialize_expression;
    private IExpression upper_bound;
    private IExpression increment;
    private IStatement statement;

    public ForStatement(String variable_name,IExpression initialize_expression, IExpression upper_bound, IExpression increment, IStatement statement)
    {
        this.variable_name = variable_name;
        this.initialize_expression = initialize_expression;
        this.upper_bound = upper_bound;
        this.increment = increment;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<IStatement> stack = state.getExeStack();
        IStatement assign = new AssignStatement(this.variable_name,this.initialize_expression);
        IStatement do_in_while = new CompoundStatement(this.statement,new AssignStatement(this.variable_name,this.increment));
        IExpression condition = new RelationalExpression("<",new VariableExpression(this.variable_name),this.upper_bound);
        IStatement while_stmt = new WhileStatement(condition,do_in_while);
        IStatement to_push = new CompoundStatement(assign,while_stmt);
        stack.push(to_push);
        //IStack<IStatement> executionStack = state.getExeStack();
        //IStatement newStatement = new CompoundStatement(new AssignStatement(this.variable_name, this.initialize_expression), new WhileStatement(new RelationalExpression("<",new VariableExpression("v"),this.upper_bound),
          //      new CompoundStatement(this.statement, new AssignStatement(this.variable_name, this.increment))));
        //executionStack.push(newStatement);
        return null;

    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException
    {
        if(typeEnv.ExistsKey(this.variable_name))
        {

            IType typeVariable = typeEnv.GetValue(this.variable_name);
            IType typeExpression = this.initialize_expression.typecheck(typeEnv);
            if (typeVariable.equals(typeExpression))
            {
                return typeEnv;
            }

            else
                throw new TypeException("AssignmentStatement: the left side and right side types don't match");
        }
        else
            throw new TypeException("the variable is not declared");
    }

    public String toString()
    {
        return "for(" + this.variable_name + "=" + this.initialize_expression.toString() + "," +
            this.variable_name + ">" + this.upper_bound.toString() + ","+this.variable_name + "="+ this.increment.toString() + ")"
            +"\n"+ this.statement.toString();
    }
}
