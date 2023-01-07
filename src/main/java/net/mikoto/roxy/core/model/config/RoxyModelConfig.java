package net.mikoto.roxy.core.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mikoto.yukino.model.config.InstantiableObject;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoxyModelConfig {
    private String modelName;
    private String resourcesStrategyName;
    private ResourceConfig[] resources;
    private TaskConfig task;
    private Map<String, InstantiableObject> storages;
}
