package domain.statement;

import domain.ProgramState;
import domain.adts.IDictionary;
import domain.adts.IStack;
import domain.type.IType;
import domain.value.IntValue;
import exceptions.StatementException;
import exceptions.TypeException;

public class SleepStatement implements IStatement{


    private IntValue number;

    public SleepStatement(IntValue number) {
        this.number = number;
    }


    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<IStatement> stack = state.getExeStack();
        if(number.GetValue() > 0)
        {
            IntValue new_value = new IntValue(this.number.GetValue() - 1);
            SleepStatement sleepy = new SleepStatement(new_value);
            stack.push(sleepy);
        }
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws TypeException {
        //we already know that it is an int value so no checking to be done here
        return typeEnv;
    }

    @Override
    public String toString() {
        return "SleepStatement{" +
                "number=" + number.toString() +
                '}';
    }
}
