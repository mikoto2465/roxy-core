package net.mikoto.roxy.core.model.config;

import lombok.Data;

import java.util.Map;

@Data
public class TaskConfig {
    private ThreadPoolConfig threadPoolConfig;
    private InstantiableAlgorithm taskAlgorithm;
    private int taskCount;
}
