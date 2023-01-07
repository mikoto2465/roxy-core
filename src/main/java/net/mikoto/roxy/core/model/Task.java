package net.mikoto.roxy.core.model;

import lombok.Data;
import net.mikoto.roxy.core.strategy.Strategy;
import net.mikoto.roxy.core.model.config.ThreadPoolConfig;
import net.mikoto.roxy.core.observer.Observer;

@Data
public class Task {
    private Strategy<?> taskStrategy;
    private Observer[] taskObservers;
    private int taskCount;
    private ThreadPoolConfig threadPoolConfig;
}
