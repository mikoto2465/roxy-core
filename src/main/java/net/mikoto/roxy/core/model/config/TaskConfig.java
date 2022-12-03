package net.mikoto.roxy.core.model.config;

import lombok.Data;

@Data
public class TaskConfig {
    private ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();
    private InstantiableAlgorithm taskAlgorithm;
    private InstantiableObject[] taskObservers;
    private int taskCount;
}
