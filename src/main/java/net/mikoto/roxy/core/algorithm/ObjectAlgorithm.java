package net.mikoto.roxy.core.algorithm;

import net.mikoto.roxy.core.annotation.AlgorithmInterface;

@AlgorithmInterface(
        value = "RoxyObjectAlgorithm",
        resultClass = Object.class
)
public interface ObjectAlgorithm extends Algorithm<Object> {
}
