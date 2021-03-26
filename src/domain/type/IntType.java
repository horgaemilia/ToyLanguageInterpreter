package domain.type;

import domain.value.IValue;
import domain.value.IntValue;

public class IntType implements IType {
     public boolean equals(Object another)
    {
        if(another instanceof IntType)
            return true;
        else
            return false;
    }

    @Override
    public IValue DefaultValue()
    {
      return new IntValue(0);
    }

    public String toString()
    {
        return "int";
    }
}
