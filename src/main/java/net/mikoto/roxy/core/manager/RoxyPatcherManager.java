package net.mikoto.roxy.core.manager;

import net.mikoto.roxy.core.RoxyPatcher;
import net.mikoto.roxy.core.model.AbstractHasAHashMapClass;
import net.mikoto.roxy.core.model.RoxyModel;
import net.mikoto.roxy.core.observer.Observer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Component("RoxyPatcherManager")
public class RoxyPatcherManager extends AbstractHasAHashMapClass<RoxyPatcher> {
    private final RoxyModelManager roxyModelManager;
    private static final List<Observer<?>> observers = new ArrayList<>();

    @Autowired
    public RoxyPatcherManager(RoxyModelManager roxyModelManager) {
        this.roxyModelManager = roxyModelManager;
    }

    /**
     * Create a patcher with a model.
     *
     * @param modelName The model name
     * @return The patcher.
     */
    public RoxyPatcher createPatcher(String modelName) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RoxyModel roxyModel = roxyModelManager.createModel(modelName);
        RoxyPatcher<?> roxyPatcher = new RoxyPatcher<>(roxyModel);
        for (Observer<?> observer : roxyModel.getTask().getTaskObservers()) {
            roxyPatcher.register(observer);
        }
        return roxyPatcher;
    }

    public void stopAll() {
        for (RoxyPatcher roxyPatcher :
                super.dataMap.values()) {
            roxyPatcher.stop();
        }
    }
}
