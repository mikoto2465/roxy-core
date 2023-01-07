package net.mikoto.roxy.core.strategy.impl;

import net.mikoto.roxy.core.strategy.StringStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
public class ForeachStringListStrategy implements StringStrategy {
    private final List<String> routeValues;
    private int currentIndex = 0;
    private final int maxIndex;

    public ForeachStringListStrategy(@NotNull List<String> routeValues) {
        this.routeValues = routeValues;
        maxIndex = routeValues.size() - 1;
    }

    @Override
    public String run(Object... objects) {
        if (currentIndex < maxIndex) {
            String routeValue = routeValues.get(currentIndex);
            currentIndex ++;
            return routeValue;
        } else {
            return null;
        }
    }
}
