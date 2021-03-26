package domain.type;

import domain.value.BoolValue;
import domain.value.IValue;

public class BoolType implements IType {
    public boolean equals(Object another)
    {
        if(another instanceof BoolType)
            return true;
        else
            return false;
    }

    @Override
    public IValue DefaultValue() {
        return new BoolValue(true);
    }

    public String toString()
    {
        return "bool";
    }
}
