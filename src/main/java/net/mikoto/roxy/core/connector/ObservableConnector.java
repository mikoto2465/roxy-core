package net.mikoto.roxy.core.connector;

import net.mikoto.roxy.core.observer.Observer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
public abstract class ObservableConnector<S> {
    private final Set<Observer<S>> OBSERVER_SET = new HashSet<>();

    public void addObserver(Observer<S> observer) {
        OBSERVER_SET.add(observer);
    }

    public void removeObserver(Observer<S> observer) {
        OBSERVER_SET.remove(observer);
    }

    public void notifyObservers(S subject) {
        for (Observer<S> observer : OBSERVER_SET) {
            observer.notify(subject);
        }
    }
}
