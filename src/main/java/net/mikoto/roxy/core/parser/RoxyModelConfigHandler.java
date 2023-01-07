package net.mikoto.roxy.core.parser;

import net.mikoto.roxy.core.manager.RoxyModelConfigManager;
import net.mikoto.roxy.core.model.config.RoxyModelConfig;
import net.mikoto.yukino.parser.FileParserHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author mikoto
 * @date 2023/1/1
 * Create for core
 */
public abstract class RoxyModelConfigHandler extends FileParserHandler<RoxyModelConfig> {
    private final RoxyModelConfigManager roxyModelConfigManager;

    public RoxyModelConfigHandler(RoxyModelConfigManager roxyModelConfigManager) {
        this.roxyModelConfigManager = roxyModelConfigManager;
    }

    @Override
    public void parserHandle(Object target) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        roxyModelConfigManager.registerModelConfig(doParse((File) target));
        super.parserHandle(target);
    }
}
