package domain.type;

import domain.value.IValue;
import domain.value.StringValue;

public class StringType implements IType{
    public boolean equals(Object another)
    {
        if(another instanceof StringType)
            return true;
        else
            return false;
    }
    @Override
    public IValue DefaultValue() {
        return new StringValue("");
    }
    public String toString()
    {
        return "string";
    }
}
