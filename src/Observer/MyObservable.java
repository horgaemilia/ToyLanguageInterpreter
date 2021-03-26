package Observer;

import java.util.ArrayList;

public class MyObservable {
    ArrayList<MyObserver> observers = new ArrayList<>();
    public void AddObserver(MyObserver observer)
    {
        observers.add(observer);
    }
    public void Notify()
    {
        observers.forEach(MyObserver::update);
    }
}
