package net.mikoto.roxy.core.observer;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Logger;

public abstract class Observer<T> {
    @Getter
    private T current;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Logger logger;

    public void update(T target) {
        this.current = target;

        doAfterUpdate(target);
    }

    public abstract void doAfterUpdate(T target);
}
