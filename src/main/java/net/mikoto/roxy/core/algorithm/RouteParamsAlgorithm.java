package net.mikoto.roxy.core.algorithm;

import net.mikoto.roxy.core.annotation.AlgorithmInterface;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@AlgorithmInterface(
        value = "RoxyRouteParamsAlgorithm",
        resultClass = String.class
)
public interface RouteParamsAlgorithm {
    String getParam();
}
