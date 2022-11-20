package net.mikoto.roxy.core.model.network.resource;

import java.util.Map;

public interface VariableResource<T> {
    T getResource(Map<?, ?> arg);
}
