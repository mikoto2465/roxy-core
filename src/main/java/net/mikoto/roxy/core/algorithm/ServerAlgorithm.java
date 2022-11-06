package net.mikoto.roxy.core.algorithm;

import net.mikoto.roxy.core.annotation.AlgorithmInterface;
import net.mikoto.roxy.core.model.network.server.HttpTarget;

/**
 * @author mikoto
 * @date 2022/10/22
 * Create for core
 */
@AlgorithmInterface(
        value = "RoxyServerAlgorithm",
        resultClass = HttpTarget.class
)
public interface ServerAlgorithm extends ObjectAlgorithm {
    HttpTarget run(Object... objects);
}
