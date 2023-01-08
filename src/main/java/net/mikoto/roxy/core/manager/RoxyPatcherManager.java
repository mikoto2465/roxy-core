package net.mikoto.roxy.core.manager;

import net.mikoto.roxy.core.RoxyPatcher;
import net.mikoto.roxy.core.model.RoxyModel;
import net.mikoto.roxy.core.observer.Observer;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;

public class RoxyPatcherManager extends RegistrableManager<RoxyPatcher> {
    private final RoxyModelManager roxyModelManager;

    @Autowired
    public RoxyPatcherManager(RoxyModelManager roxyModelManager) {
        this.roxyModelManager = roxyModelManager;
    }

    public RoxyPatcher createPatcher(RoxyModel roxyModel) {
        RoxyPatcher roxyPatcher = new RoxyPatcher(roxyModel);
        for (Observer observer : roxyModel.getTask().getTaskObservers()) {
            roxyPatcher.register(observer);
        }
        return roxyPatcher;
    }

    public RoxyPatcher createPatcher(String modelName) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RoxyModel roxyModel = roxyModelManager.createModel(modelName, null);
        return createPatcher(roxyModel);
    }

    public void stopAll() {
        for (RoxyPatcher roxyPatcher :
                super.dataMap.values()) {
            roxyPatcher.stop();
        }
    }
}
