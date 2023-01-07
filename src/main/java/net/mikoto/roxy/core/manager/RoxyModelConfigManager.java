package net.mikoto.roxy.core.manager;

import lombok.NoArgsConstructor;
import net.mikoto.roxy.core.model.AbstractHasAHashMapClass;
import net.mikoto.roxy.core.model.config.RoxyModelConfig;

/**
 * @author mikoto
 * @date 2022/10/23
 * Create for core
 */
@NoArgsConstructor
public class RoxyModelConfigManager extends AbstractHasAHashMapClass<RoxyModelConfig> {
    public void registerModelConfig(RoxyModelConfig roxyModelConfig) {
        this.put(roxyModelConfig.getModelName(), roxyModelConfig);
    }
}
