package domain.adts;

import exceptions.StackException;

import java.util.stream.Stream;

public interface IStack<IType> {
    void push(IType element);
    IType pop() throws StackException;
    boolean isEmpty();
    String toString();
    Stream<IType> stream();
}
