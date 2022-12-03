package net.mikoto.roxy.core.observer;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;

@Log4j2
public abstract class Observer {
    @Getter
    private Object current;
    @Getter
    @Setter
    private Logger logger = log;

    public void update(Object target) {
        this.current = target;

        doAfterUpdate(target);
    }

    public abstract void doAfterUpdate(Object target);
}
