package net.mikoto.roxy.core.manager;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStringObjectHashMapManager<T> {
    protected final Map<String, T> dataMap = new HashMap<>();

    public T get(String name) {
        return dataMap.get(name);
    }

    public void put(String name, T object) {
        dataMap.put(name, object);
    }
}
