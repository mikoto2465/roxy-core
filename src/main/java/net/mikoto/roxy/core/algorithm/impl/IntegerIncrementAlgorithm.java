package net.mikoto.roxy.core.algorithm.impl;

import net.mikoto.roxy.core.algorithm.StringAlgorithm;
import net.mikoto.roxy.core.annotation.AlgorithmImpl;

@AlgorithmImpl(
        value = "RoxyIntegerIncrementAlgorithm",
        constructorParamsClasses = {
                Integer.class, Integer.class
        }
)
public class IntegerIncrementAlgorithm implements StringAlgorithm {
    private Integer last;
    private final Integer end;
    public IntegerIncrementAlgorithm(Integer start, Integer end) {
        if (start > end) {
            throw new RuntimeException("Start number cannot bigger than end number");
        }
        this.end = end;
        last = start - 1;
    }

    @Override
    public String run(Object... objects) {
        if (last >= end) {
            return null;
        }
        last++;
        return String.valueOf(last);
    }
}
