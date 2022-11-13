package net.mikoto.roxy.core.scanner.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.manager.ConfigModelManager;
import net.mikoto.roxy.core.model.Config;
import net.mikoto.roxy.core.model.config.RoxyModelConfig;
import net.mikoto.roxy.core.scanner.FileHandler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static net.mikoto.roxy.core.util.FileUtil.readFile;

@Log4j2
public class ModelConfigHandler extends FileHandler {
    private final Config config;
    private final ConfigModelManager configModelManager;

    public ModelConfigHandler(Config config, ConfigModelManager configModelManager) {
        this.config = config;
        this.configModelManager = configModelManager;
    }

    @Override
    protected void doHandle(@NotNull File file) throws IOException {
        if (file.getName().endsWith(config.getConfigSuffix())) {
            RoxyModelConfig roxyModelConfig = JSON.parseObject(readFile(file), RoxyModelConfig.class);
            configModelManager.registerModelConfig(roxyModelConfig);
            log.info("[Roxy] Found model config -> " + roxyModelConfig.getModelName());
        }
    }
}
