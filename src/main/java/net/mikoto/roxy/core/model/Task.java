package net.mikoto.roxy.core.model;

import lombok.Data;
import net.mikoto.roxy.core.algorithm.Algorithm;
import net.mikoto.roxy.core.model.config.InstantiableObject;
import net.mikoto.roxy.core.model.config.ThreadPoolConfig;
import net.mikoto.roxy.core.observer.Observer;

import java.util.Map;

@Data
public class Task {
    private Algorithm<?> taskAlgorithm;
    private Observer<?>[] taskObservers;
    private int taskCount;
    private ThreadPoolConfig threadPoolConfig;
}
