package net.mikoto.roxy.core.strategy.impl;

import net.mikoto.roxy.core.strategy.ObjectStrategy;
import net.mikoto.roxy.core.annotation.AlgorithmImpl;

/**
 * @author mikoto
 * @date 2022/10/23
 * Create for core
 */
public class StaticObjectStrategy implements ObjectStrategy {
    private final Object object;

    public StaticObjectStrategy(Object object) {
        this.object = object;
    }

    public Object run(Object... objects) {
        return object;
    }
}
