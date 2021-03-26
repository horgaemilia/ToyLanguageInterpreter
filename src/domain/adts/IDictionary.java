package domain.adts;


import exceptions.DictionaryException;

import java.util.Map;

public interface IDictionary<IType1,IType2> {
    void put(IType1 key, IType2 value) throws DictionaryException;
    void replace(IType1 key,IType2 new_value) throws DictionaryException;
    IType2 GetValue(IType1 key) throws  DictionaryException;
    boolean ExistsKey(IType1 key);
    String toString();
    void delete(IType1 key);
    Map<IType1,IType2> GetContent();
    IDictionary<IType1,IType2> cloneDictionary();
}
