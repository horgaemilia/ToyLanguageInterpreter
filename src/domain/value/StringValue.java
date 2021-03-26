package domain.value;

import domain.type.IType;
import domain.type.StringType;

public class StringValue implements  IValue{
    private final String value;

    public StringValue(String value)
    {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    @Override
    public StringType getType()
    {
        return new StringType();
    }

    public boolean equals(Object another)
    {
        if(another instanceof StringValue)
        {
            StringValue anotherStringValue = (StringValue)another;
            if(anotherStringValue.getValue().equals(this.value))
                return true;
            else
                return false;
        }
        return false;
    }

    public String getValue() {
        return value;
    }
}
