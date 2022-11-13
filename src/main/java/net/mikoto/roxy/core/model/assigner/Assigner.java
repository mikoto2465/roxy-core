package net.mikoto.roxy.core.model.assigner;

import java.util.Map;

public interface Assigner {
    Map<Object, Object> assign(Map<Object, Object> inputValue);

    void put(Object k, Object v);
}
