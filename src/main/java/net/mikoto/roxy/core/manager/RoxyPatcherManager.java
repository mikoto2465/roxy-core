package net.mikoto.roxy.core.manager;

import net.mikoto.roxy.core.RoxyPatcher;
import net.mikoto.roxy.core.model.AbstractHasAHashMapClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("RoxyPatcherManager")
public class RoxyPatcherManager extends AbstractHasAHashMapClass<RoxyPatcher> {
    private final RoxyModelManager roxyModelManager;

    @Autowired
    public RoxyPatcherManager(RoxyModelManager roxyModelManager) {
        this.roxyModelManager = roxyModelManager;
    }

    public void stopAll() {
        for (RoxyPatcher roxyPatcher :
                super.dataMap.values()) {
            roxyPatcher.stop();
        }
    }
}
