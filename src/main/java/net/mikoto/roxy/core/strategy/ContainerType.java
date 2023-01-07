package net.mikoto.roxy.core.strategy;

import java.util.List;

/**
 * @author mikoto
 * @date 2022/10/23
 * Create for core
 */
public enum ContainerType {
    LIST(List.class),
    NO_CONTAINER(null);

    private final Class<?> clazz;

    ContainerType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
