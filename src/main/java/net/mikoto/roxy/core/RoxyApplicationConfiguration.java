package net.mikoto.roxy.core;

import net.mikoto.roxy.core.manager.RoxyModelConfigManager;
import net.mikoto.roxy.core.manager.RoxyModelManager;
import net.mikoto.roxy.core.manager.RoxyPatcherManager;
import net.mikoto.yukino.YukinoApplicationConfiguration;
import net.mikoto.yukino.manager.YukinoModelManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author mikoto
 * @date 2023/1/4
 * Create for core
 */
@Configuration
@Import(YukinoApplicationConfiguration.class)
public class RoxyApplicationConfiguration {
    @Bean
    public RoxyModelConfigManager roxyModelConfigManager() {
        return new RoxyModelConfigManager();
    }

    @Bean
    public RoxyModelManager roxyModelManager(RoxyModelConfigManager roxyModelConfigManager, YukinoModelManager yukinoModelManager) {
        return new RoxyModelManager(roxyModelConfigManager, yukinoModelManager);
    }

    @Bean
    public RoxyPatcherManager roxyPatcherManager(RoxyModelManager roxyModelManager) {
        return new RoxyPatcherManager(roxyModelManager);
    }
}
