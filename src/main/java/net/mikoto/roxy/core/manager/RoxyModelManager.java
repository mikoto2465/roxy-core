package net.mikoto.roxy.core.manager;

import net.mikoto.roxy.core.algorithm.Algorithm;
import net.mikoto.roxy.core.model.*;
import net.mikoto.roxy.core.model.config.InstantiableObject;
import net.mikoto.roxy.core.model.config.ResourceConfig;
import net.mikoto.roxy.core.model.config.RoxyModelConfig;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component("RoxyModelManager")
public class RoxyModelManager extends AbstractHasAHashMapClass<RoxyModel> {
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
        RoxyModelConfig roxyModelConfig = configModelManager.get(modelName);

        roxyModel.setModelName(modelName);

        roxyModel.setRoxyDataModelClass(dataModelManager.get(modelName));

        // Get resource object
        for (int i = 0; i < roxyModelConfig.getResources().length; i++) {
            ResourceConfig resourceConfig = roxyModelConfig.getResources()[i];

            // Instance the params object
            InstantiableObject[] algorithmImplParams = resourceConfig.getAlgorithmParams();
            Object[] params = new Object[algorithmImplParams.length];
            for (int j = 0; j < params.length; j++) {
                params[j] = InstantiableObject.instance(algorithmImplParams[j]);
            }

            // Create resource algorithm
            Algorithm<?> resourceAlgorithm = algorithmManager.createAlgorithmByName(resourceConfig.getAlgorithmName(), params);
            Resource resource = new Resource(resourceConfig.getResourceName(), resourceAlgorithm);
            roxyModel.getResources().put(resourceConfig.getResourceName(), resource);
        }

        super.put(modelName, roxyModel);

        return roxyModel;
    }
}
