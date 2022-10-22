package net.mikoto.roxy.core.algorithm.impl;

import net.mikoto.roxy.core.algorithm.ServerAlgorithm;
import net.mikoto.roxy.core.annotation.AlgorithmImpl;
import net.mikoto.roxy.core.model.network.server.Server;

/**
 * @author mikoto
 * @date 2022/10/23
 * Create for core
 */
@AlgorithmImpl(
        value = "RoxyStaticServerAlgorithm",
        constructorParamsClasses = {
                Server.class
        }
)
public class StaticServerAlgorithm implements ServerAlgorithm {
    private final Server server;

    public StaticServerAlgorithm(Server server) {
        this.server = server;
    }

    @Override
    public Server run() {
        return server;
    }
}
