package net.mikoto.roxy.core.algorithm.impl;

import net.mikoto.roxy.core.algorithm.StringAlgorithm;
import net.mikoto.roxy.core.annotation.AlgorithmImpl;
import net.mikoto.roxy.core.model.network.server.HttpServer;
import net.mikoto.roxy.core.model.network.server.Server;

/**
 * @author mikoto
 * @date 2022/10/22
 * Create for core
 */
@AlgorithmImpl("RoxyStaticStringAlgorithm")
public class StaticStringAlgorithm implements StringAlgorithm {
    private final String s;
    public StaticStringAlgorithm(String s) {
        this.s = s;
    }

    @Override
    public String run() {
        return s;
    }
}
