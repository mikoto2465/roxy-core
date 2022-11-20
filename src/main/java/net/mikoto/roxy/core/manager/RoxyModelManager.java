package net.mikoto.roxy.core.manager;

import net.mikoto.roxy.core.algorithm.Algorithm;
import net.mikoto.roxy.core.model.*;
import net.mikoto.roxy.core.model.config.InstantiableAlgorithm;
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
            // Instance resource
            ResourceConfig resourceConfig = roxyModelConfig.getResources()[i];

            // Create algorithm in each resource
            Algorithm<?> resourceAlgorithm = InstantiableAlgorithm.instance(algorithmManager, resourceConfig.getResourceAlgorithm());
            Resource resource = new Resource(resourceConfig.getWeight(), resourceConfig.getResourceName(), resourceAlgorithm);
            roxyModel.getResources().put(resourceConfig.getResourceName(), resource);
        }

        // Create resource algorithm
        Algorithm<?> resourcesAlgorithm = algorithmManager.createAlgorithmByName(
                roxyModelConfig.getResourcesAlgorithmName(),
                roxyModel.getResources().values().toArray()
        );
        roxyModel.setResourcesAlgorithm(resourcesAlgorithm);

        super.put(modelName, roxyModel);

        return roxyModel;
    }
}
