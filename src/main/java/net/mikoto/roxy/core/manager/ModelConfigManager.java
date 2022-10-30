package net.mikoto.roxy.core.manager;

import lombok.NoArgsConstructor;
import net.mikoto.roxy.core.model.Config;
import net.mikoto.roxy.core.model.ModelConfig;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mikoto
 * @date 2022/10/23
 * Create for core
 */
@Component("RoxyTaskManager")
@NoArgsConstructor
public class ModelConfigManager {
    private static final Map<String, ModelConfig> modelConfigMap = new HashMap<>();

    public void registerModelConfig(String modelName, ModelConfig modelConfig) {
        modelConfigMap.put(modelName, modelConfig);
    }
}
