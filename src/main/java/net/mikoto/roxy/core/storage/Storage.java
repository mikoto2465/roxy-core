package net.mikoto.roxy.core.storage;

import net.mikoto.roxy.core.model.RoxyDataModel;

public interface Storage {
    void store(String path, String name, RoxyDataModel roxyDataModel) throws Exception;
}
