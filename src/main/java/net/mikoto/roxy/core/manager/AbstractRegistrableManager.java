package net.mikoto.roxy.core.manager;

import net.mikoto.roxy.core.model.AbstractHasAHashMapClass;

public class AbstractRegistrableManager<T> extends AbstractHasAHashMapClass<T> {
    public void register(String name , T subject) {
        super.dataMap.put(name, subject);
    }
}
