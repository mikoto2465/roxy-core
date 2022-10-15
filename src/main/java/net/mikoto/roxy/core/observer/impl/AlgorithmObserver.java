package net.mikoto.roxy.core.observer.impl;

import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.algorithm.Algorithm;
import net.mikoto.roxy.core.algorithm.Statue;
import net.mikoto.roxy.core.model.Config;
import net.mikoto.roxy.core.observer.Observer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@Component("RoxyAlgorithmObserver")
@Log4j2
public class AlgorithmObserver implements Observer<Class<Algorithm>> {
    private final Config config;

    @Autowired
    public AlgorithmObserver(Config config) {
        this.config = config;
    }

    @Override
    public void notify(Class<Algorithm> subject, Object... objects) {
        if (config.isLogging()) {
            if (objects[0] instanceof Statue) {
                if (objects[0].equals(Statue.Found)) {
                    log.info("Found algorithm class -> " + subject.getName());
                } else if (objects[0].equals(Statue.Created)) {
                    if (objects[1] instanceof Algorithm) {
                        log.info("Created algorithm class -> " + subject.getName() + " -> " + objects[1]);
                    } else {
                        log.info("Created algorithm class -> " + subject.getName());
                    }
                }
            } else {
                log.info("Found algorithm class -> " + subject.getName());
            }
        }
    }
}
