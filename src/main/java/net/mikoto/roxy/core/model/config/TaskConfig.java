package net.mikoto.roxy.core.model.config;

import lombok.Data;

import java.util.Map;

@Data
public class TaskConfig {
    private ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();
    private InstantiableAlgorithm taskAlgorithm;
    private int taskCount;
}
