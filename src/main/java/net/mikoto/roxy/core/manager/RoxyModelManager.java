package net.mikoto.roxy.core.manager;

import net.mikoto.roxy.core.model.*;
import net.mikoto.roxy.core.model.config.*;
import net.mikoto.roxy.core.observer.Observer;
import net.mikoto.roxy.core.storage.Storage;
import net.mikoto.yukino.manager.YukinoModelManager;

import java.util.HashMap;
import java.util.Map;

public class RoxyModelManager extends RegistrableManager<RoxyModel> {
    private final RoxyModelConfigManager roxyModelConfigManager;
    private final YukinoModelManager yukinoModelManager;

    public RoxyModelManager(RoxyModelConfigManager roxyModelConfigManager, YukinoModelManager yukinoModelManager) {
        this.roxyModelConfigManager = roxyModelConfigManager;
        this.yukinoModelManager = yukinoModelManager;
    }

    public RoxyModel createModel(String modelName, String yukinoModelName) {
        if (yukinoModelName == null) {
            yukinoModelName = modelName;
        }
        RoxyModel roxyModel = new RoxyModel();
        RoxyModelConfig roxyModelConfig = roxyModelConfigManager.get(modelName);

        roxyModel.setModelName(modelName);

        roxyModel.setYukinoModel(yukinoModelManager.get(modelName));

        // Get resource object
        for (int i = 0; i < roxyModelConfig.getResources().length; i++) {
            // Instance resource
            ResourceConfig resourceConfig = roxyModelConfig.getResources()[i];

            // Create algorithm in each resource
            Strategy<?> resourceStrategy = InstantiableStrategy.instance(strategyManager, resourceConfig.getResourceStrategy());
            Resource resource = new Resource(resourceConfig.getWeight(), resourceConfig.getResourceName(), resourceStrategy);
            roxyModel.getResources().put(resourceConfig.getResourceName(), resource);
        }

        // Create resource algorithm
        Strategy<?> resourcesStrategy = strategyManager.createAlgorithmByName(
                roxyModelConfig.getResourcesStrategyName(),
                roxyModel.getResources().values().toArray()
        );
        roxyModel.setResourcesStrategy(resourcesStrategy);

        // Instance storages
        Map<String, Storage> storageMap = new HashMap<>();
        for (Map.Entry<String, InstantiableObject> entry : roxyModelConfig.getStorages().entrySet()){
            storageMap.put(entry.getKey(), (Storage) InstantiableObject.instance(entry.getValue()));
        }
        roxyModel.setStorages(storageMap);

        // Create task
        Task task = new Task();
        TaskConfig taskConfig = roxyModelConfig.getTask();
        task.setTaskStrategy(
                InstantiableStrategy.instance(strategyManager, taskConfig.getTaskStrategy())
        );

        // Instance observers
        Observer[] observers = new Observer[taskConfig.getTaskObservers().length];
        for (int i = 0; i < taskConfig.getTaskObservers().length; i++) {
            observers[i] = (Observer) InstantiableObject.instance(taskConfig.getTaskObservers()[i]);
        }
        task.setTaskObservers(observers);
        task.setTaskCount(taskConfig.getTaskCount());
        task.setThreadPoolConfig(taskConfig.getThreadPoolConfig());
        roxyModel.setTask(task);

        super.register(modelName, roxyModel);

        return roxyModel;
    }
}
