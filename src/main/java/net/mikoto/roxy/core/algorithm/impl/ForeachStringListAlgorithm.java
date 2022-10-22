package net.mikoto.roxy.core.algorithm.impl;

import net.mikoto.roxy.core.algorithm.ContainerType;
import net.mikoto.roxy.core.algorithm.StringAlgorithm;
import net.mikoto.roxy.core.annotation.AlgorithmImpl;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@AlgorithmImpl(
        value = "RoxyStringListForeachAlgorithm",
        constructorParamsContainers = {
                ContainerType.LIST
        }
)
public class ForeachStringListAlgorithm implements StringAlgorithm {
    private final List<String> routeValues;
    private int currentIndex = 0;
    private final int maxIndex;

    public ForeachStringListAlgorithm(@NotNull List<String> routeValues) {
        this.routeValues = routeValues;
        maxIndex = routeValues.size() - 1;
    }

    @Override
    public String run() {
        if (currentIndex < maxIndex) {
            String routeValue = routeValues.get(currentIndex);
            currentIndex ++;
            return routeValue;
        } else {
            return null;
        }
    }
}
