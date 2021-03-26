package domain.value;

import domain.type.IntType;

public class IntValue implements IValue {
    private final int value;
    public IntValue(int v)
    {
        this.value = v;
    }
    @Override
    public IntType getType()
    {
        return new IntType();
    }

    public int GetValue()
    {
        return this.value;
    }

    public String toString()
    {
       return String.valueOf(this.value);
    }

    public boolean equals(Object another)
    {
        if(another instanceof  IntValue)
        {
            IntValue anotherIntValue = (IntValue)another;
            if(anotherIntValue.GetValue() == this.value)
                return true;
            else
                return  false;
        }
        else
            return false;
    }
}
