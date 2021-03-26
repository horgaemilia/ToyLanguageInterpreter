package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.adts.IStack;
import domain.expression.IExpression;
import domain.type.IType;
import domain.value.IValue;
import exceptions.DictionaryException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import exceptions.TypeException;


public class AssignStatement implements IStatement {

    private String id;
    private IExpression expression;

    public AssignStatement(String id, IExpression expression)
    {
        this.id = id;
        this.expression = expression;
    }


    @Override
    public ProgramState execute(ProgramState state) throws StatementException
    {
        IStack<IStatement> stack = state.getExeStack();
        IDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();

        if(symTable.ExistsKey(this.id))
        {

                IValue value = this.expression.evaluate(symTable,heap);

                    IType typeId = (symTable.GetValue(this.id)).getType();
                    if(value.getType().equals(typeId))
                    {
                        symTable.replace(this.id,value);
                    }
                    else throw new StatementException("declared type of variable " + id + " and type of the assigned expression do not match");

        }
        else throw new StatementException("the used variable " + id + " was not declared before");
        return null;
    }


    public String toString()
    {
        return this.id + " = " + this.expression.toString();
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        if(typeEnv.ExistsKey(id))
        {
            IType typeVariable = typeEnv.GetValue(id);
            IType typeExpression = this.expression.typecheck(typeEnv);
            if (typeVariable.equals(typeExpression))
                return typeEnv;
            else
                throw new TypeException("AssignmentStatement: the left side and right side types don't match");
        }
        else
            throw new TypeException("the variable is not declared");
    }
}
