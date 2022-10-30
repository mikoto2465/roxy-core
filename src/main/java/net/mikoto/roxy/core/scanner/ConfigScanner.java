package net.mikoto.roxy.core.scanner;

import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.manager.DataModelManager;
import net.mikoto.roxy.core.manager.ConfigModelManager;
import net.mikoto.roxy.core.model.Config;
import net.mikoto.roxy.core.scanner.handler.FileScannerStartHandler;
import net.mikoto.roxy.core.scanner.handler.ModelConfigHandler;
import net.mikoto.roxy.core.scanner.handler.ModelHandler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import static net.mikoto.roxy.core.util.FileUtil.createDir;

@Component
@Log4j2
public class ConfigScanner {
    private final FileHandler fileScannerStartHandler;
    private final Config config;

    public ConfigScanner(Config config, DataModelManager dataModelManager, ConfigModelManager configModelManager) throws IOException {
        this.config = config;
        fileScannerStartHandler = new FileScannerStartHandler();
        ModelHandler modelHandler = new ModelHandler(config, dataModelManager);
        ModelConfigHandler modelConfigHandler = new ModelConfigHandler(config, configModelManager);

        fileScannerStartHandler
                .setNext(modelHandler)
                .setNext(modelConfigHandler)
                .setNext(null);

        doScan();
    }

    public void doScan() throws IOException {
        // To make sure the dir is existed.
        createDir(config.getConfigPath());
        File configDir = new File(System.getProperty("user.dir") + config.getConfigPath());
        File[] modelFiles = configDir.listFiles(
                pathname -> pathname.isDirectory() || pathname.getAbsolutePath().endsWith(".json")
        );

        // Looking for models.
        if (modelFiles != null && modelFiles.length > 0) {
            for (File file :
                    modelFiles) {
                fileScannerStartHandler.handle(file);
            }
        } else {
            log.warn("[Roxy] No file was found");
        }
    }
}