package net.mikoto.roxy.core.strategy;


public interface Strategy<T> {
    /**
     * Run this algorithm.
     *
     * @param objects The objects.
     * @return The result.
     */
    T run(Object... objects);
}
