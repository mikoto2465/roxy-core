package net.mikoto.roxy.core.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RoxyModel {
    private String modelName;
    private Class<? extends RoxyDataModel> roxyDataModelClass;
    private Map<String, Resource> resources = new HashMap<>();
    private Map<String, Storage> storages = new HashMap<>();
}