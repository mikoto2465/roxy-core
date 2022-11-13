package net.mikoto.roxy.core.algorithm;

public interface Shardable<T> {
    T[] shard(int piecesCount);
}
