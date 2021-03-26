package domain.adts;

import domain.statement.CompoundStatement;
import exceptions.StackException;
import view.command.Command;

import java.util.Stack;
import java.util.stream.Stream;

public class MyStack<IType> implements IStack<IType>{
    private Stack<IType> mystack;
    public MyStack()
    {
        this.mystack = new Stack<>();
    }
    @Override
    public void push(IType element)
    {
        this.mystack.push(element);
    }

    @Override
    public IType pop() throws StackException
    {
        if(this.mystack.size() == 0)
            throw  new StackException("the stack is full");
        IType value = this.mystack.pop();
        return value;
    }

    public Stack<IType> GetAll()
    {
        return this.mystack;
    }

    @Override
    public boolean isEmpty() {
        if(this.mystack.size() == 0)
            return true;
        return false;
    }

    @Override
    public String toString()
    {
        return mystack.toString();
    }

    @Override
    public Stream<IType> stream()
    {
        return this.mystack.stream();
    }

    public String myToString() {
        String msg = "";
        if (mystack.size() > 0)
        {
            if(!(mystack.peek() instanceof CompoundStatement))
            msg = msg + mystack.peek().toString() + "\n";
        }
        if (mystack.size() == 2)
        {
            if(!(mystack.get(0) instanceof CompoundStatement))
                msg = msg + mystack.get(0).toString() + "\n";
        }
        for (var element : mystack) {
            if (element instanceof CompoundStatement)
            {
                CompoundStatement el = (CompoundStatement) element;
                msg = msg + el.recursivePrint(el) + "\n";
            }
        }
        return msg;
    }
}
