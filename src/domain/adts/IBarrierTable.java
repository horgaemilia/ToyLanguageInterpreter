package domain.adts;

import domain.value.IValue;
import exceptions.HeapException;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface IBarrierTable {
    void put(Integer key, Pair<Integer, List<Integer>> value) throws HeapException;
    Pair<Integer, List<Integer>> GetValue(Integer key) throws  HeapException;
    Integer GetFreeAddress();
    boolean ExistsKey(Integer key);
    String toString();
    void deallocate(Integer key);
    Integer allocate(Pair<Integer, List<Integer>> value);
    void SetContent(Map<Integer, Pair<Integer, List<Integer>>> newHeap);
    Map<Integer, Pair<Integer, List<Integer>>> GetContent();

}
