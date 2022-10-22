package net.mikoto.roxy.core.algorithm.impl;

import net.mikoto.roxy.core.algorithm.ServerAlgorithm;
import net.mikoto.roxy.core.annotation.AlgorithmImpl;
import net.mikoto.roxy.core.model.network.server.HttpServer;
import net.mikoto.roxy.core.model.network.server.Server;

/**
 * @author mikoto
 * @date 2022/10/22
 * Create for core
 */
@AlgorithmImpl("RoxyStaticStringAlgorithm")
public class StaticStringAlgorithm implements ServerAlgorithm {
    private final HttpServer httpServer;
    public StaticStringAlgorithm(String address) {
        httpServer = new HttpServer(address);
    }

    @Override
    public Server run() {
        return httpServer;
    }
}
