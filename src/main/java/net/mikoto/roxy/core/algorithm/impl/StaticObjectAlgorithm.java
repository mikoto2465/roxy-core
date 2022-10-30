package net.mikoto.roxy.core.algorithm.impl;

import net.mikoto.roxy.core.algorithm.Algorithm;
import net.mikoto.roxy.core.algorithm.ObjectAlgorithm;
import net.mikoto.roxy.core.annotation.AlgorithmImpl;

/**
 * @author mikoto
 * @date 2022/10/23
 * Create for core
 */
@AlgorithmImpl(
        value = "RoxyStaticObjectAlgorithm",
        constructorParamsClasses = {
                Object.class
        }
)
public class StaticObjectAlgorithm implements ObjectAlgorithm {
    private final Object object;

    public StaticObjectAlgorithm(Object object) {
        this.object = object;
    }

    public Object run(Object... objects) {
        return object;
    }
}
