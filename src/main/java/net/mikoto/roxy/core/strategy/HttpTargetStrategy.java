package net.mikoto.roxy.core.strategy;

import net.mikoto.roxy.core.annotation.AlgorithmInterface;
import net.mikoto.roxy.core.model.network.resource.HttpTarget;

/**
 * @author mikoto
 * @date 2022/10/22
 * Create for core
 */
public interface HttpTargetStrategy extends ObjectStrategy {
    HttpTarget run(Object... objects);
}
