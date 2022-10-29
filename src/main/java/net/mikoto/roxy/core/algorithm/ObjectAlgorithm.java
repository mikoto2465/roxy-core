package net.mikoto.roxy.core.algorithm;

import net.mikoto.roxy.core.annotation.AlgorithmInterface;

/**
 * @author mikoto
 * @date 2022/10/23
 * Create for core
 */
@AlgorithmInterface(
        value = "RoxyServerAlgorithm",
        resultClass = Object.class
)
public interface ObjectAlgorithm {
    Object run();
}
