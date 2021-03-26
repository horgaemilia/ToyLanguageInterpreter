package domain.adts;

import exceptions.HeapException;

import java.util.HashMap;
import java.util.Map;

public class MyLatchTable implements ILatchTable{

    private HashMap<Integer, Integer> myLatch;
    private Integer nextfree;

    public MyLatchTable()
    {
        myLatch = new HashMap<>();
        nextfree = 1;
    }

    @Override
    public void put(Integer key, Integer value) throws HeapException
    {
        this.myLatch.put(key, value);
    }

    @Override
    public Integer GetValue(Integer key) throws HeapException {
        return this.myLatch.get(key);
    }

    @Override
    public boolean ExistsKey(Integer key) {
        return this.myLatch.containsKey(key);
    }

    @Override
    public void SetContent(Map<Integer, Integer> newHeap)
    {
        this.myLatch.clear();
        this.myLatch.putAll(newHeap);
    }

    @Override
    public Map<Integer, Integer> GetContent() {
        return this.myLatch;
    }

    @Override
    public void deallocate(Integer key)
    {
        this.myLatch.remove(key);
    }

    @Override
    public Integer allocate(Integer value) {
        this.myLatch.put(nextfree,value);
        this.nextfree++;
        return  nextfree-1;
    }

    @Override
    public Integer GetFreeAddress() {
        return this.nextfree;
    }
}
