package net.mikoto.roxy.core.algorithm;

import net.mikoto.roxy.core.annotation.AlgorithmInterface;
import net.mikoto.roxy.core.model.network.resource.HttpTarget;

/**
 * @author mikoto
 * @date 2022/10/22
 * Create for core
 */
@AlgorithmInterface(
        value = "RoxyHttpTargetAlgorithm",
        resultClass = HttpTarget.class
)
public interface HttpTargetAlgorithm extends ObjectAlgorithm {
    HttpTarget run(Object... objects);
}
