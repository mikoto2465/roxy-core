package net.mikoto.roxy.core.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoxyModelConfig {
    private String modelName;
    private String resourcesAlgorithmName;
    private ResourceConfig[] resources;
}
