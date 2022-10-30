package net.mikoto.roxy.core.model;

import lombok.Data;
import net.mikoto.roxy.core.algorithm.Algorithm;

@Data
public class RoxyModel {
    private String modelName;
    private Class<? extends RoxyDataModel> roxyDataModelClass;
    private Algorithm<?>[] source;
}