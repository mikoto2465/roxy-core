package net.mikoto.roxy.core.scanner;

import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.manager.ModelManager;
import net.mikoto.roxy.core.manager.ModelConfigManager;
import net.mikoto.roxy.core.model.Config;
import net.mikoto.roxy.core.scanner.handler.ModelHandler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import static net.mikoto.roxy.core.util.FileUtil.createDir;

@Component
@Log4j2
public class ConfigScanner {
    private final ModelHandler modelHandler;
    private final Config config;

    public ConfigScanner(Config config, ModelManager modelManager, ModelConfigManager taskManager) throws IOException {
        this.config = config;
        modelHandler = new ModelHandler(config, modelManager);
        modelHandler.setNext(null);
        doScan();
    }

    public void doScan() throws IOException {
        // To make sure the dir is existed.
        createDir(config.getConfigPath());
        File configDir = new File(System.getProperty("user.dir") + config.getConfigPath());
        File[] modelFiles = configDir.listFiles(
                pathname -> pathname.isDirectory() || pathname.getAbsolutePath().endsWith(config.getModelSuffix())
        );

        // Looking for models.
        if (modelFiles != null && modelFiles.length > 0) {
            for (File file :
                    modelFiles) {
                modelHandler.handle(file);
            }
        } else {
            log.warn("[Roxy] No file was found");
        }
    }
}