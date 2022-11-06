package net.mikoto.roxy.core.algorithm;

import net.mikoto.roxy.core.annotation.AlgorithmInterface;
import net.mikoto.roxy.core.model.network.server.Server;

/**
 * @author mikoto
 * @date 2022/10/22
 * Create for core
 */
@AlgorithmInterface(
        value = "RoxyServerAlgorithm",
        resultClass = Server.class
)
public interface ServerAlgorithm extends ObjectAlgorithm {
    Server run(Object... objects);
}
