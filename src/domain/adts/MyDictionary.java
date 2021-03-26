package domain.adts;

import exceptions.DictionaryException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<IType1,IType2> implements IDictionary<IType1,IType2>{
    private HashMap<IType1,IType2> mydictionary;

    public MyDictionary()
    {
        this.mydictionary = new HashMap<IType1, IType2>();
    }


    @Override
    public void put(IType1 key, IType2 value) throws DictionaryException
    {
        if(this.mydictionary.containsKey(key))
            throw new DictionaryException("the key already exists in the dictionary");
        this.mydictionary.put(key,value);
    }

    @Override
    public void replace(IType1 key, IType2 new_value) throws DictionaryException
    {
        if(!this.mydictionary.containsKey(key))
            throw new DictionaryException("the key does not exist in the dictionary");
        this.mydictionary.replace(key,new_value);
    }

    @Override
    public IType2 GetValue(IType1 key) throws DictionaryException {
        if(!this.mydictionary.containsKey(key))
            throw new DictionaryException("the key does not exist in the dictionary");
        IType2 value = this.mydictionary.get(key);
        return value;
    }

    @Override
    public boolean ExistsKey(IType1 key) {
        return this.mydictionary.containsKey(key);
    }

    @Override
    public String toString()
    {
        StringBuilder msg = new StringBuilder();
        IType1 key;
        IType2 value;
        for(var set : this.mydictionary.entrySet())
            msg.append(set.getKey().toString()).append("->").append(set.getValue().toString()).append("\n");
        return msg.toString();
    }

    public Map<IType1,IType2> GetContent()
    {
        return this.mydictionary;
    }

    @Override
    public IDictionary<IType1, IType2> cloneDictionary() {
        IDictionary<IType1,IType2> myClone = new MyDictionary<IType1, IType2>();
        for(IType1 key : this.mydictionary.keySet())
            myClone.put(key,this.mydictionary.get(key));
        return myClone;
    }

    @Override
    public void delete(IType1 key)
    {
       if(ExistsKey(key))
       {
           IType2 value = this.mydictionary.get(key);
           this.mydictionary.remove(key,value);
       }
       else
            return;
    }
}
