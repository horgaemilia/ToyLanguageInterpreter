package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.type.IType;
import exceptions.StatementException;
import exceptions.TypeException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws StatementException;
    String toString();
    IDictionary<String, IType> typecheck(IDictionary<String,IType> typeEnv) throws TypeException;
}
