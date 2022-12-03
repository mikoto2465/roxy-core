package net.mikoto.roxy.core.observer;

import java.util.ArrayList;

public abstract class Observable {
    private final ArrayList<Observer> observers = new ArrayList<>();

    public Observable register(Observer observer) {
        observers.add(observer);
        return this;
    }

    public Observable remove(Observer observer) {
        observers.remove(observer);
        return this;
    }

    public void notify(Object target) {
        for (Observer observer :
                observers) {
            observer.update(target);
        }
    }
}
