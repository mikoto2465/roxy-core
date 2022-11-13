package net.mikoto.roxy.core.manager;

import lombok.AllArgsConstructor;
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
public class ConfigModelManager extends AbstractStringObjectHashMapManager<RoxyConfigModel> {
    public void registerModelConfig(RoxyConfigModel roxyConfigModel) {
        this.put(roxyConfigModel.getModelName(), roxyConfigModel);
    }
}
