package net.mikoto.roxy.core.manager;

import net.mikoto.yukino.manager.HasAHashMapClass;

public class RegistrableManager<T> extends HasAHashMapClass<T> {
    public void register(String name , T subject) {
        super.dataMap.put(name, subject);
    }
}
