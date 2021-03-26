package domain.adts;

import exceptions.HeapException;

import java.util.Map;

public interface ILatchTable {
    void put(Integer key, Integer value) throws HeapException;
    Integer GetValue(Integer key) throws  HeapException;
    boolean ExistsKey(Integer key);
    String toString();
    void SetContent(Map<Integer, Integer> newHeap);
    Map<Integer,Integer> GetContent();
    void deallocate(Integer key);
    Integer allocate(Integer value);
    Integer GetFreeAddress();
}
