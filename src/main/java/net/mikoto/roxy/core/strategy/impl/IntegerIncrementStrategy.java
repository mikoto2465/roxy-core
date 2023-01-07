package net.mikoto.roxy.core.strategy.impl;

import net.mikoto.roxy.core.strategy.ShardableStrategy;
import net.mikoto.roxy.core.strategy.StringStrategy;
import net.mikoto.roxy.core.annotation.AlgorithmImpl;
import net.mikoto.yukino.strategy.Strategy;

public class IntegerIncrementStrategy implements StringStrategy, ShardableStrategy<String> {
    private int last;
    private final int end;
    public IntegerIncrementStrategy(Integer start, Integer end) {
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
    public Strategy<String>[] shard(int piecesCount) {
        int stepSize = (int) Math.floor((double) end / (double) piecesCount);
        Strategy<String>[] strategies = new IntegerIncrementStrategy[piecesCount];
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
                strategies[i] = new IntegerIncrementStrategy(startNum, endNum);
            }
        }
        return strategies;
    }
}
