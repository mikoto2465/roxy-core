package net.mikoto.roxy.core.strategy;

import net.mikoto.yukino.strategy.Strategy;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
public interface StringStrategy extends Strategy<String> {
    String run(Object... objects);
}
