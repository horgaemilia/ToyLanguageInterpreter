package domain.value;

import domain.type.IType;

public interface IValue {
    IType getType();
    boolean equals(Object another);
}
