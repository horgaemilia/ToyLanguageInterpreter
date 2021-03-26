package domain.adts;

import exceptions.ListException;

import java.util.ArrayList;
import java.util.List;

public class MyList<IType> implements IList<IType> {
    private ArrayList<IType> mylist ;
    public MyList(){
        this.mylist = new ArrayList<>();
    }
    public MyList(MyList<IType> otherList)
    {
        this.mylist = otherList.mylist;
    }

    @Override
    public void append(IType element) {
        this.mylist.add(element);
    }

    @Override
    public IType remove(int position) throws ListException {
        if(this.mylist.size() <= position)
            throw new ListException("the element does not exist in the list");
        IType deleted = this.mylist.get(position);
        this.mylist.remove(position);
        return deleted;
    }

    @Override
    public IType removeLast(){
        IType deleted = this.mylist.get(this.mylist.size()-1);
        this.mylist.remove(this.mylist.size()-1);
        return deleted;
    }

    @Override
    public boolean IsEmpty() {
        if(this.mylist.size() == 0)
            return true;
        return false;
    }

    @Override
    public ArrayList<IType> RetrieveAll() {
        return this.mylist;
    }

    @Override
    public String toString()
    {
        return mylist.toString();
    }

    @Override
    public void SetContent(List<IType> newContent) {
        this.mylist.clear();
        this.mylist.addAll(newContent);
    }

    public Integer Size()
    {
        return this.mylist.size();
    }
}
