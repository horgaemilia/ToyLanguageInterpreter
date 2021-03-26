package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.IStack;
import domain.type.IType;
import exceptions.StatementException;
import exceptions.TypeException;

public class CompoundStatement implements IStatement {
    private IStatement first;
    private IStatement second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<IStatement> stack = state.getExeStack();
        stack.push(this.second);
        stack.push(this.first);
        return null;
    }

    public IStatement getFirst() {
        return first;
    }

    public IStatement getSecond() {
        return second;
    }

    public String toString() {
        return "(" + this.first.toString() + ";" + this.second.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        return second.typecheck(first.typecheck(typeEnv));
    }


    public static String recursivePrint(IStatement statement)
    {
        StringBuilder show = new StringBuilder();
        if (statement instanceof CompoundStatement) {
            CompoundStatement compound = (CompoundStatement) statement;
            show.append(recursivePrint(compound.getFirst()));
            show.append(recursivePrint(compound.getSecond()));
        } else {
            show.append(statement).append("\n");
        }
        return show.toString();

    }
}
