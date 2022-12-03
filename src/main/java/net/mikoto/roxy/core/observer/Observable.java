package net.mikoto.roxy.core.observer;

import java.util.ArrayList;

public abstract class Observable<T> {
    private final ArrayList<Observer<T>> observers = new ArrayList<>();

    public Observable<T> register(Observer<T> observer) {
        observers.add(observer);
        return this;
    }

    public Observable<T> remove(Observer<T> observer) {
        observers.remove(observer);
        return this;
    }

    public void notify(T target) {
        for (Observer<T> observer :
                observers) {
            observer.update(target);
        }
    }
}
