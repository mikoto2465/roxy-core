package net.mikoto.roxy.core.model;

import lombok.Data;
import net.mikoto.roxy.core.storage.Storage;
import net.mikoto.yukino.model.YukinoModel;

import java.util.HashMap;
import java.util.Map;

@Data
public class RoxyModel {
    private String modelName;
    private YukinoModel yukinoModel;
    private Map<String, Resource> resources = new HashMap<>();
    private Strategy<?> resourcesStrategy;
    private Map<String, Storage> storages = new HashMap<>();
    private Task task;
}