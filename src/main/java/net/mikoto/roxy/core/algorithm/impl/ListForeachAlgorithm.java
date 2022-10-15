package net.mikoto.roxy.core.algorithm.impl;

import net.mikoto.roxy.core.algorithm.RouteParamsGenerateAlgorithm;
import net.mikoto.roxy.core.annotation.Algorithm;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@Algorithm("ListForeachAlgorithm")
public class ListForeachAlgorithm implements RouteParamsGenerateAlgorithm {
    private final List<String> routeValues;
    private int currentIndex = 0;
    private final int maxIndex;

    public ListForeachAlgorithm(@NotNull List<String> routeValues) {
        this.routeValues = routeValues;
        maxIndex = routeValues.size() - 1;
    }

    @Override
    public String getParam() {
        if (currentIndex < maxIndex) {
            String routeValue = routeValues.get(currentIndex);
            currentIndex ++;
            return routeValue;
        } else {
            return null;
        }
    }
}
