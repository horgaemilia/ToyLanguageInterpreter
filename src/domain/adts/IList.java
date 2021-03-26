package domain.adts;

import exceptions.ListException;

import java.util.ArrayList;
import java.util.List;

public interface IList<IType> {
    void append(IType element);

    IType remove(int position) throws ListException;

    IType removeLast();

    boolean IsEmpty();

    ArrayList<IType> RetrieveAll();


    String toString();

    void SetContent(List<IType> newContent);
}
