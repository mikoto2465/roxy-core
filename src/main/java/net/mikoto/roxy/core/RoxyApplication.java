package net.mikoto.roxy.core;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.manager.RoxyModelConfigManager;
import net.mikoto.roxy.core.manager.RoxyModelManager;
import net.mikoto.roxy.core.manager.RoxyPatcherManager;

/**
 * @author mikoto
 * @date 2023/1/1
 * Create for core
 */
@Getter
@Log4j2
public class RoxyApplication {
    private final RoxyModelConfigManager roxyModelConfigManager;
    private final RoxyModelManager roxyModelManager;
    private final RoxyPatcherManager roxyPatcherManager;

    public RoxyApplication(RoxyModelConfigManager roxyModelConfigManager, RoxyModelManager roxyModelManager, RoxyPatcherManager roxyPatcherManager) {
        this.roxyModelConfigManager = roxyModelConfigManager;
        this.roxyModelManager = roxyModelManager;
        this.roxyPatcherManager = roxyPatcherManager;
        log.info("[Roxy] Started");
    }
}
