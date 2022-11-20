package net.mikoto.roxy.core.model;

import lombok.Data;
import net.mikoto.roxy.core.algorithm.Algorithm;
import net.mikoto.roxy.core.model.assigner.Assigner;

import java.util.HashMap;
import java.util.Map;

@Data
public class RoxyModel {
    private String modelName;
    private Class<? extends RoxyDataModel> roxyDataModelClass;
    private Map<String, Resource> resources = new HashMap<>();
    private Algorithm<?> resourcesAlgorithm;
    private Map<String, Storage> storages = new HashMap<>();
    private Task task;
}