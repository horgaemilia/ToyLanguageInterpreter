package domain.type;

import domain.value.IValue;

public interface IType {
    boolean equals(Object another);
    IValue DefaultValue();
}
