package domain;

import domain.adts.*;
import domain.statement.IStatement;
import domain.value.BoolValue;
import domain.value.IValue;
import domain.value.StringValue;
import exceptions.StackException;
import exceptions.StatementException;

import java.io.BufferedReader;

public class ProgramState {
    private IStack<IStatement> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private IHeap heap;
    private IStatement originalProgram;
    private static int NextId=0;
    private int ProgramId;
    private ILock lock;
    private IBarrierTable barrierTable;
    private ILatchTable latchTable;

    public ProgramState(IStack<IStatement> exeStack,IDictionary<String,IValue> symTable,IList<IValue> out,IDictionary<StringValue, BufferedReader> fileTable,IStatement program,IHeap heap)
    {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = program;
        this.heap = heap;
        this.exeStack.push(program);
        this.ProgramId = getNextId();
    }

    public ProgramState(IStack<IStatement> exeStack,IDictionary<String,IValue> symTable,IList<IValue> out,IDictionary<StringValue, BufferedReader> fileTable,IStatement program,IHeap heap,ILock lock)
    {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = program;
        this.heap = heap;
        this.exeStack.push(program);
        this.ProgramId = getNextId();
        this.lock = lock;
    }
    public ProgramState(IStack<IStatement> exeStack,IDictionary<String,IValue> symTable,IList<IValue> out,IDictionary<StringValue, BufferedReader> fileTable,IStatement program,IHeap heap,IBarrierTable barrierTable)
    {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = program;
        this.heap = heap;
        this.exeStack.push(program);
        this.ProgramId = getNextId();
        this.barrierTable = barrierTable;
    }
    public ProgramState(IStack<IStatement> exeStack,IDictionary<String,IValue> symTable,IList<IValue> out,IDictionary<StringValue, BufferedReader> fileTable,IStatement program,IHeap heap,ILatchTable latchTable)
    {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = program;
        this.heap = heap;
        this.exeStack.push(program);
        this.ProgramId = getNextId();
        this.latchTable = latchTable;
    }

    public static synchronized int getNextId()
    {
        int id = NextId;
        NextId+=1;
        return id;
    }

    public IDictionary<StringValue,BufferedReader> getFileTable()
    {
        return this.fileTable;
    }
    public IStack<IStatement> getExeStack()
    {
        return this.exeStack;
    }

    public IDictionary<String, IValue> getSymTable()
    {
        return this.symTable;
    }

    public IList<IValue> getOut()
    {
        return this.out;
    }

    public IStatement getOriginalProgram()
    {
        return this.originalProgram;
    }

    public String toString()
    {
        return "Program Id:" + this.ProgramId +" \n Program State{ " + " stack= " + this.exeStack.toString() + "\n symbol table= " + this.symTable.toString() + "\n out= " + this.out.toString() + "}\n heap table= " +this.heap.toString()+ "}\n file table=" + this.fileTable.toString() ;
        //+ "\n"
          //      + "lock table: " + this.lock.toString() + "\n";
    }

    public IHeap getHeap() {
        return this.heap;
    }

    public boolean isNotCompleted()
    {
        if(this.exeStack.isEmpty())
            return false;
        return true;
    }

    public ProgramState oneStep() throws StackException, StatementException
    {
        if(this.exeStack.isEmpty())
        {
            throw new StackException("the stack is empty");
        }
        IStatement statement = exeStack.pop();
        return statement.execute(this);
    }

    public int GetId()
    {
        return this.ProgramId;
    }

    public ILock getLock() {
        return lock;
    }

    public void setLock(ILock lock) {
        this.lock = lock;
    }

    public void setSymTable(IDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public Integer getLockAddress()
    {
        return this.lock.GetFreeAddress();
    }
    public Integer getBarrierAddress()
    {
        return this.barrierTable.GetFreeAddress();
    }

    public IBarrierTable getBarrierTable()
    {
        return barrierTable;
    }

    public void setBarrierTable(IBarrierTable barrierTable) {
        this.barrierTable = barrierTable;
    }

    public ILatchTable getLatchTable() {
        return latchTable;
    }

    public void setLatchTable(ILatchTable latchTable) {
        this.latchTable = latchTable;
    }

    public Integer getLatchAddress()
    {
        return this.latchTable.GetFreeAddress(); //do not forget to increment the next free if you don't use allocate
    }
}
