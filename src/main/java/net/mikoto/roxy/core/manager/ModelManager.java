package net.mikoto.roxy.core.manager;

import net.mikoto.roxy.core.model.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

import static net.mikoto.roxy.core.util.FileUtil.createDir;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@Component("RoxyModelManager")
public class ModelManager {
    @Autowired
    public ModelManager(Config config) {
        createDir(config.getModelPath());
        File dir = new File(config.getModelPath());
        File[] files = dir.listFiles(
                pathname -> pathname.isDirectory() || pathname.getAbsolutePath().endsWith(".roxy.model.json")
        );

    }
}
