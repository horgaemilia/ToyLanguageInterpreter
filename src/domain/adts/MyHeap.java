package domain.adts;

import domain.type.IType;
import domain.value.IValue;
import exceptions.HeapException;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements IHeap{
    private HashMap<Integer,IValue> myheap;
    private Integer nextFree;

    public MyHeap()
    {
        this.myheap = new HashMap<>();
        nextFree = 1;
    }

    @Override
    public void put(Integer key, IValue value) throws HeapException
    {
        this.myheap.put(key, value);
    }

    @Override
    public IValue GetValue(Integer key) throws HeapException {
        return myheap.get(key);
    }

    @Override
    public Integer GetFreeAddress() {
        return nextFree;
    }

    @Override
    public boolean ExistsKey(Integer key) {
        return this.myheap.containsKey(key);
    }

    @Override
    public void deallocate(Integer key) {
        this.myheap.remove(key);

    }

    @Override
    public Integer allocate(IValue value)
    {
        this.myheap.put(nextFree,value);
        this.nextFree++;
        return  nextFree-1;
    }

    @Override
    public void SetContent(Map<Integer, IValue> newHeap)
    {
        this.myheap.clear();
        this.myheap.putAll(newHeap);

    }

    @Override
    public Map<Integer, IValue> GetContent() {
        return this.myheap;
    }


    public String toString()
    {
        StringBuilder msg = new StringBuilder();
        Integer key;
        IValue value;
        for(var set : this.myheap.entrySet())
            msg.append(set.getKey().toString()).append("->").append(set.getValue().toString()).append("\n");
        return msg.toString();
    }
}
