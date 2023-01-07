package net.mikoto.roxy.core.parser.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.manager.RoxyModelConfigManager;
import net.mikoto.roxy.core.model.Config;
import net.mikoto.roxy.core.model.config.RoxyModelConfig;
import net.mikoto.roxy.core.parser.RoxyModelConfigHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static net.mikoto.roxy.core.util.FileUtil.readFile;

@Log4j2
public class RoxyModelConfigParser extends RoxyModelConfigHandler {
    private final Config config;

    public RoxyModelConfigParser(RoxyModelConfigManager roxyModelConfigManager, Config config) {
        super(roxyModelConfigManager);
        this.config = config;
    }

    @Override
    protected RoxyModelConfig doParse(File file) throws IOException {
        if (file.getName().endsWith(config.getConfigSuffix())) {
            return JSON.parseObject(readFile(file), RoxyModelConfig.class);
        }
        return null;
    }
}
