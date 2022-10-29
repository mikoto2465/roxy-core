package net.mikoto.roxy.core.algorithm;

public interface Algorithm<T> {
    /**
     * Run this algorithm.
     *
     * @param objects The objects.
     * @return The result.
     */
    T run(Object... objects);
}
