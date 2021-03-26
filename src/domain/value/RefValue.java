package domain.value;

import domain.type.IType;
import domain.type.RefType;

public class RefValue implements IValue {
    private int address;
    private IType type;

    public RefValue(int address,IType type)
    {
        this.address = address;
        this.type = type;
    }
    @Override
    public IType getType()
    {
        return new RefType(type);
    }

    public IType getInnerType()
    {
        return this.type;
    }

    public int getAddress() {
        return address;
    }

    public int GetAddress()
    {
        return this.address;
    }

    public void SetAdress(int address)
    {
        this.address = address;
    }

    public String toString()
    {
        return "adress " + this.address + " type " + this.type.toString();
    }

}
