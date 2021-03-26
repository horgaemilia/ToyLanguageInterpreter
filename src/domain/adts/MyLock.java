package domain.adts;

import domain.value.IValue;
import exceptions.HeapException;

import java.util.HashMap;
import java.util.Map;

public class MyLock implements ILock{

    private HashMap<Integer, Integer> mylock;
    private Integer nextfree;

    public MyLock()
    {
        this.mylock = new HashMap<>();
        nextfree = 1;
    }

    @Override
    public void put(Integer key, Integer value) throws HeapException
    {
        this.mylock.put(key, value);
    }

    @Override
    public Integer GetValue(Integer key) throws HeapException {
        return mylock.get(key);
    }



    @Override
    public boolean ExistsKey(Integer key) {
        return this.mylock.containsKey(key);
    }


    @Override
    public void SetContent(Map<Integer, Integer> newHeap)
    {
        this.mylock.clear();
        this.mylock.putAll(newHeap);

    }

    @Override
    public Map<Integer, Integer> GetContent() {
        return this.mylock;
    }

    @Override
    public void deallocate(Integer key) {
        this.mylock.remove(key);
    }

    @Override
    public Integer allocate(Integer value) {
        this.mylock.put(nextfree,value);
        this.nextfree++;
        return  nextfree-1;
    }

    @Override
    public Integer GetFreeAddress() {
        return nextfree;
    }


   /* public String toString()
    {
        StringBuilder msg = new StringBuilder();
        Integer key;
        Integer value;
        for(var set : this.mylock.entrySet())
            msg.append(set.getKey().toString()).append("->").append(set.getValue().toString()).append("\n");
        return msg.toString();
    }

    */
}
