package net.mikoto.roxy.core.scanner.handler;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.manager.ModelConfigManager;
import net.mikoto.roxy.core.model.Config;
import net.mikoto.roxy.core.model.ModelConfig;
import net.mikoto.roxy.core.scanner.FileHandler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static net.mikoto.roxy.core.util.FileUtil.readFile;

@Log4j2
public class ModelConfigHandler extends FileHandler {
    private final Config config;
    private final ModelConfigManager modelConfigManager;

    public ModelConfigHandler(Config config, ModelConfigManager modelConfigManager) {
        this.config = config;
        this.modelConfigManager = modelConfigManager;
    }

    @Override
    protected void doHandle(@NotNull File file) throws IOException {
        if (file.getName().endsWith(config.getConfigSuffix())) {
            JSONObject modelJson = JSONObject.parseObject(readFile(file));
            String modelName = modelJson.getString("modelName");
            modelConfigManager.registerModelConfig(modelName, modelJson.to(ModelConfig.class));
            log.info("[Roxy] Found model config -> " + modelName);
        }
    }
}
