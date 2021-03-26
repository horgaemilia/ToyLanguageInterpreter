package domain.adts;

import domain.value.IValue;
import exceptions.HeapException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBarrier implements IBarrierTable{

    private Map<Integer,Pair<Integer,List<Integer>>> barrier;
    private Integer nextfree;

    public MyBarrier()
    {
        this.barrier = new HashMap<>();
        nextfree = 1;
    }

    @Override
    public void put(Integer key, Pair<Integer, List<Integer>> value) throws HeapException {
        this.barrier.put(key,value);

    }

    @Override
    public Pair<Integer, List<Integer>> GetValue(Integer key) throws HeapException {
        return this.barrier.get(key);
    }

    @Override
    public Integer GetFreeAddress() {
        return this.nextfree;
    }

    @Override
    public boolean ExistsKey(Integer key) {
        return this.barrier.containsKey(key);
    }

    @Override
    public void deallocate(Integer key)
    {
        this.barrier.remove(key);
    }

    @Override
    public Integer allocate(Pair<Integer, List<Integer>> value) {
        this.barrier.put(nextfree,value);
        this.nextfree++;
        return  nextfree-1;
    }

    @Override
    public void SetContent(Map<Integer, Pair<Integer, List<Integer>>> newHeap) {
        this.barrier = newHeap;

    }

    @Override
    public Map<Integer, Pair<Integer, List<Integer>>> GetContent() {
        return null;
    }
}
