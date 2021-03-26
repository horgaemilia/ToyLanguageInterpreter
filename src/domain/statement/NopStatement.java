package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.type.IType;
import exceptions.StatementException;
import exceptions.TypeException;

public class NopStatement implements IStatement{

    public NopStatement() {};

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        return null;
    }

    public String toString()
    {
        return "nop";
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        return typeEnv;
    }
}
