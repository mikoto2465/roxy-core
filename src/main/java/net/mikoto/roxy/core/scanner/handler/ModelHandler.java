package net.mikoto.roxy.core.scanner.handler;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.manager.ModelManager;
import net.mikoto.roxy.core.model.Config;
import net.mikoto.roxy.core.scanner.FileHandler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static net.mikoto.roxy.core.manager.ModelManager.generateModelClass;
import static net.mikoto.roxy.core.util.FileUtil.readFile;

@Log4j2
public class ModelHandler extends FileHandler {
    private final Config config;
    private final ModelManager modelManager;

    public ModelHandler(Config config, ModelManager modelManager) {
        this.config = config;
        this.modelManager = modelManager;
    }

    @Override
    protected void doHandle(@NotNull File file) throws IOException {
        if (file.getName().endsWith(config.getModelSuffix())) {
            JSONObject modelJson = JSONObject.parseObject(readFile(file));
            String modelName = modelJson.getString("modelName");
            modelManager.registerModel(modelName, generateModelClass(config.getModelPackage(), modelJson));
            log.info("[Roxy] Found model -> " + modelName);
        }
    }
}
