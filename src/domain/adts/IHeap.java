package domain.adts;

import domain.value.IValue;
import exceptions.HeapException;


import java.util.HashMap;
import java.util.Map;

public interface IHeap{
    void put(Integer key, IValue value) throws HeapException;
    IValue GetValue(Integer key) throws  HeapException;
    Integer GetFreeAddress();
    boolean ExistsKey(Integer key);
    String toString();
    void deallocate(Integer key);
    Integer allocate(IValue value);
    void SetContent(Map<Integer, IValue> newHeap);
    Map<Integer,IValue> GetContent();
}
