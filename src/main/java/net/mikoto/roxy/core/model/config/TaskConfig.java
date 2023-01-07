package net.mikoto.roxy.core.model.config;

import lombok.Data;
import net.mikoto.yukino.model.config.InstantiableObject;

@Data
public class TaskConfig {
    private ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();
    private InstantiableObject taskStrategy;
    private InstantiableObject[] taskObservers;
    private int taskCount;
}
