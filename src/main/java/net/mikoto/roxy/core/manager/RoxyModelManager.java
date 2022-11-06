package net.mikoto.roxy.core.manager;

import net.mikoto.roxy.core.algorithm.Algorithm;
import net.mikoto.roxy.core.model.InstantiableObject;
import net.mikoto.roxy.core.model.RoxyConfigModel;
import net.mikoto.roxy.core.model.RoxyModel;
import net.mikoto.roxy.core.model.ResourceConfig;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Component("RoxyModelManager")
public class RoxyModelManager {
    private static final Map<String, RoxyModel> roxyModelMap = new HashMap<>();

    private final DataModelManager dataModelManager;
    private final ConfigModelManager configModelManager;
    private final AlgorithmManager algorithmManager;

    public RoxyModelManager(DataModelManager dataModelManager, ConfigModelManager configModelManager, AlgorithmManager algorithmManager) {
        this.dataModelManager = dataModelManager;
        this.configModelManager = configModelManager;
        this.algorithmManager = algorithmManager;
    }

    public RoxyModel createModel(String modelName) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RoxyModel roxyModel = new RoxyModel();
        RoxyConfigModel roxyConfigModel = configModelManager.getModelConfig(modelName);

        roxyModel.setModelName(modelName);

        roxyModel.setRoxyDataModelClass(dataModelManager.getDataModelClass(modelName));

        // Get resource object
        for (int i = 0; i < roxyConfigModel.getResources().length; i++) {
            ResourceConfig resourceConfig = roxyConfigModel.getResources()[i];

            // Instance the params object
            InstantiableObject[] algorithmImplParams = resourceConfig.getAlgorithmImplParams();
            Object[] params = new Object[algorithmImplParams.length];
            for (int j = 0; j < params.length; j++) {
                params[j] = InstantiableObject.instance(algorithmImplParams[j]);
            }

            // Create resource algorithm
            Algorithm<?> resource = algorithmManager.createAlgorithmByName(resourceConfig.getAlgorithmImplName(), params);
            roxyModel.getResources().put(resourceConfig.getResourceName(), resource);
        }

        roxyModelMap.put(modelName, roxyModel);

        return roxyModel;
    }

    public RoxyModel getRoxyModel(String roxyModelName) {
        return roxyModelMap.get(roxyModelName);
    }
}
