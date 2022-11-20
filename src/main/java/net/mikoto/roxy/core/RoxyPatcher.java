package net.mikoto.roxy.core;

import net.mikoto.roxy.core.model.RoxyModel;

public class RoxyPatcher {
    private final RoxyModel roxyModel;
    private boolean startFlag = false;

    public RoxyPatcher(RoxyModel roxyModel) {
        this.roxyModel = roxyModel;
    }

    public void stop() {
        startFlag = false;
    }

    public void start() {
        if (!startFlag) {
            startFlag = true;
        }
    }
}
