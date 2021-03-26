package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.IHeap;
import domain.expression.IExpression;
import domain.type.IType;
import domain.type.RefType;
import domain.value.IValue;
import domain.value.RefValue;
import exceptions.StatementException;
import exceptions.TypeException;

public class NewStatement implements IStatement{
    private String variable_name;
    private IExpression expression;

    public NewStatement(String variable_name, IExpression expression)
    {

        this.variable_name = variable_name;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();

        if(symTable.ExistsKey(variable_name))
        {
            IValue variableValue = symTable.GetValue(variable_name);
            IValue  expressionValue = this.expression.evaluate(symTable,heap);
            if(variableValue.getType().equals(new RefType(expressionValue.getType())))
            {
                int address = heap.allocate(expressionValue);
                RefValue refValue = new RefValue(address,expressionValue.getType());
                symTable.replace(variable_name,refValue);
            }
            else
                throw new StatementException("the type is not a reftype or the inner type is not the type we search for");
        }
        else
            throw new StatementException(this.variable_name + " + is not defined");
        return null;
    }

    public String toString()
    {
        return "New{" + this.variable_name + "," + this.expression.toString() + "}";
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        IType typeVariable = typeEnv.GetValue(variable_name);
        IType typeExpression = this.expression.typecheck(typeEnv);
        if(typeVariable.equals(new RefType(typeExpression)))
        {
            return typeEnv;
        }
        else
            throw new TypeException("NEW stmt: right hand side and left hand side have\n" +
                    "different types");
    }
}
