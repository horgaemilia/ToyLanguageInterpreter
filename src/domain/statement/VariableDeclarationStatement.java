package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.type.*;
import domain.value.*;
import exceptions.DictionaryException;
import exceptions.StatementException;
import exceptions.TypeException;

public class VariableDeclarationStatement implements IStatement{
    private String name;
    private IType type;

    public VariableDeclarationStatement(String name,IType type)
    {
      this.name = name;
      this.type = type;
    }
    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IDictionary<String, IValue> symTable = state.getSymTable();

        if(symTable.ExistsKey(name))
            throw  new StatementException("the variable is already declared");
        if(this.type.equals(new IntType()))
        {
                symTable.put(name,new IntValue(0));
        }
        else
        {
            if(this.type.equals(new BoolType()))
            {
                    symTable.put(name,new BoolValue(true));

            }
            else
                if(this.type.equals(new StringType()))
                {
                    symTable.put(name,new StringValue(""));
                }
                else
                    if(this.type instanceof RefType)
                    {
                        IType innerType = ((RefType) this.type).getInner();
                        symTable.put(name,new RefValue(0,innerType));
                    }
                    else
                        throw new StatementException(" type of declaration does not exist");
        }
        return null;

    }
    public String toString()
    {
        return this.type.toString() + " " + this.name.toString();
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        typeEnv.put(name,type);
        return typeEnv;
    }
}
