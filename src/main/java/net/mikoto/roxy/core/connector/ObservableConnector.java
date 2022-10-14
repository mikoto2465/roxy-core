package net.mikoto.roxy.core.connector;

import net.mikoto.roxy.core.observer.Observer;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
public abstract class ObservableConnector<S> {
    private final Set<Observer<S>> OBSERVER_SET = new HashSet<>();
}
