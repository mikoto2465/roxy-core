package net.mikoto.roxy.core.manager;

import net.mikoto.roxy.core.algorithm.Algorithm;
import net.mikoto.roxy.core.algorithm.ObjectAlgorithm;
import net.mikoto.roxy.core.model.InstantiableObject;
import net.mikoto.roxy.core.model.RoxyConfigModel;
import net.mikoto.roxy.core.model.RoxyModel;
import net.mikoto.roxy.core.model.SourceConfig;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
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

        Algorithm<?>[] sourceAlgorithms = new ObjectAlgorithm[roxyConfigModel.getSources().length];
        for (int i = 0; i < sourceAlgorithms.length; i++) {
            SourceConfig sourceConfig = roxyConfigModel.getSources()[i];
            InstantiableObject[] algorithmImplParams = sourceConfig.getAlgorithmImplParams();
            Object[] params = new Object[algorithmImplParams.length];
            for (int j = 0; j < params.length; j++) {
                params[j] = InstantiableObject.instance(algorithmImplParams[j]);
            }

            sourceAlgorithms[i] = algorithmManager.createAlgorithmByName(sourceConfig.getAlgorithmImplName(), params);
        }
        roxyModel.setSource(sourceAlgorithms);

        roxyModelMap.put(modelName, roxyModel);

        return roxyModel;
    }
}
