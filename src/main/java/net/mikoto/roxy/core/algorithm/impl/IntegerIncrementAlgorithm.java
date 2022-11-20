package net.mikoto.roxy.core.algorithm.impl;

import net.mikoto.roxy.core.algorithm.Algorithm;
import net.mikoto.roxy.core.algorithm.ShardableAlgorithm;
import net.mikoto.roxy.core.algorithm.StringAlgorithm;
import net.mikoto.roxy.core.annotation.AlgorithmImpl;

@AlgorithmImpl(
        value = "RoxyIntegerIncrementAlgorithm",
        constructorParamsClasses = {
                Integer.class, Integer.class
        }
)
public class IntegerIncrementAlgorithm implements StringAlgorithm, ShardableAlgorithm<String> {
    private int last;
    private final int end;
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

    @Override
    public Algorithm<String>[] shard(int piecesCount) {
        int stepSize = (int) Math.floor((double) end / (double) piecesCount);
        Algorithm<String>[] algorithms = new IntegerIncrementAlgorithm[piecesCount];
        int remainder = end - (piecesCount * stepSize);
        int currentRemainder = remainder;
        for (int i = 0; i < piecesCount; i++) {
            int startNum = stepSize * i + 1;
            int endNum = stepSize * (i + 1);

            if (currentRemainder > 0) {
                startNum = startNum + i;
                endNum = endNum + i + 1;
                currentRemainder--;
            } else {
                startNum = startNum + remainder;
                endNum = endNum + remainder;
            }

            if (startNum <= endNum) {
                algorithms[i] = new IntegerIncrementAlgorithm(startNum, endNum);
            }
        }
        return algorithms;
    }
}
