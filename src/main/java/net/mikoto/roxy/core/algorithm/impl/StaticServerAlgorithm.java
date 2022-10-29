package net.mikoto.roxy.core.algorithm.impl;

import net.mikoto.roxy.core.algorithm.ObjectAlgorithm;
import net.mikoto.roxy.core.algorithm.ServerAlgorithm;
import net.mikoto.roxy.core.annotation.AlgorithmImpl;
import net.mikoto.roxy.core.model.network.server.Server;

/**
 * @author mikoto
 * @date 2022/10/23
 * Create for core
 */
@AlgorithmImpl(
        value = "RoxyStaticObjectAlgorithm",
        constructorParamsClasses = {
                Server.class
        }
)
public class StaticServerAlgorithm implements ObjectAlgorithm {
    private final Object object;

    public StaticServerAlgorithm(Object object) {
        this.object = object;
    }

    public Object run() {
        return object;
    }
}
