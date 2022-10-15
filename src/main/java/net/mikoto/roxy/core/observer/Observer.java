package net.mikoto.roxy.core.observer;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
public interface Observer<S> {
    void notify(S subject, Object... objects);
}
