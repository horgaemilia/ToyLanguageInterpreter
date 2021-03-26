package domain.value;


import domain.type.BoolType;

public class BoolValue implements IValue {
    private boolean value;
    public BoolValue(boolean v)
    {
        this.value = v;
    }

    @Override
    public BoolType getType() {
        return new BoolType();
    }
    public boolean GetValue()
    {
        return this.value;
    }

    public String toString()
    {
        return String.valueOf(this.value);
    }

    public boolean equals(Object another)
    {
        if(another instanceof BoolValue)
        {
            BoolValue anotherBool = (BoolValue)another;
            if(anotherBool.GetValue() == this.value)
                return true;
            else
                return false;
        }
        else
            return false;
    }
}
