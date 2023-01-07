package net.mikoto.roxy.core.strategy;

public interface Shardable<T> {
    T[] shard(int piecesCount);
}
