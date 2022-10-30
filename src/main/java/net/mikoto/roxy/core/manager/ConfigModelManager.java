package net.mikoto.roxy.core.manager;

import lombok.NoArgsConstructor;
import net.mikoto.roxy.core.model.RoxyConfigModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mikoto
 * @date 2022/10/23
 * Create for core
 */
@Component("RoxyConfigModelManager")
@NoArgsConstructor
public class ConfigModelManager {
    private static final Map<String, RoxyConfigModel> modelConfigMap = new HashMap<>();

    public void registerModelConfig(RoxyConfigModel roxyConfigModel) {
        modelConfigMap.put(roxyConfigModel.getModelName(), roxyConfigModel);
    }

    public RoxyConfigModel getModelConfig(String modelName) {
        return modelConfigMap.get(modelName);
    }
}
