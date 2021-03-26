package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.adts.IStack;
import domain.expression.IExpression;
import domain.type.BoolType;
import domain.type.IType;
import domain.value.IValue;
import exceptions.StatementException;
import exceptions.TypeException;

public class ConditionalStatement implements IStatement{

    private String variable_name;
    private IExpression expression1;
    private IExpression expression2;
    private IExpression expression3;

    public ConditionalStatement(String variable_name, IExpression expression1, IExpression expression2, IExpression expression3) {
        this.variable_name = variable_name;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException
    {
        IStack<IStatement> stack = state.getExeStack();
        IStatement assign1 = new AssignStatement(this.variable_name,this.expression2);
        IStatement assign2 = new AssignStatement(this.variable_name,this.expression3);
        IStatement if_statement = new IfStatement(this.expression1,assign1,assign2);
        stack.push(if_statement);
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        IType typeExpression = this.expression1.typecheck(typeEnv);
        if (typeExpression.equals(new BoolType())) {

        } else
            throw new TypeException(this.expression1.toString() + " is not of bool type");
        IType typeExpression2 = this.expression2.typecheck(typeEnv);
        if (typeExpression2.equals(new BoolType())) {

        } else
            throw new TypeException(this.expression2.toString() + " is not of bool type");
        IType typeExpression3 = this.expression3.typecheck(typeEnv);
        if (typeExpression3.equals(new BoolType())) {

        } else
            throw new TypeException(this.expression3.toString() + " is not of bool type");
        if (typeEnv.ExistsKey(this.variable_name)) {
            IType typeVariable = typeEnv.GetValue(this.variable_name);
            if (typeVariable.equals(typeExpression))
                return typeEnv;
            else
                throw new TypeException("AssignmentStatement: the left side and right side types don't match");
        } else
            throw new TypeException("the variable is not declared");
    }

    @Override
    public String toString() {
        return "ConditionalStatement{" +
                 variable_name + '=' +
                 expression1.toString() +"?"+
                expression2.toString() + ":"+
                expression3.toString() +
                '}';
    }
}
