package net.mikoto.roxy.core.model;

import lombok.Data;
import net.mikoto.roxy.core.algorithm.Algorithm;

import java.util.HashMap;
import java.util.Map;

@Data
public class RoxyModel {
    private String modelName;
    private Class<? extends RoxyDataModel> roxyDataModelClass;
    private Map<String, Algorithm<?>> resources = new HashMap<>();
}