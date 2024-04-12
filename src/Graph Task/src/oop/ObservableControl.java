package oop;

import java.util.ArrayList;
import java.util.List;

public class ObservableControl implements Observable {

    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(BinaryResult binaryResult) {
        for (Observer observer : observers) {
            observer.update(binaryResult);
        }
    }
}
