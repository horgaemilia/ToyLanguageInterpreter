package domain.type;

import domain.value.IValue;
import domain.value.RefValue;

public class RefType implements IType {
    private IType inner;

    public RefType(IType inner)
    {
        this.inner = inner;
    }
    @Override
    public IValue DefaultValue()
    {
        return new RefValue(0,inner);
    }

    public boolean equals(Object another)
    {
        if(another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
            else
                return false;
    }
    public IType getInner()
    {
        return inner;
    }
    public String toString()
    {
        return "Ref("+this.inner.toString()+")";
    }
}
