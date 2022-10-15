package net.mikoto.roxy.core.observer.impl;

import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.configuration.Config;
import net.mikoto.roxy.core.observer.Observer;
import net.mikoto.roxy.core.model.network.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@Component("RoxyServerObject")
@Log4j2
public class ServerObserver implements Observer<Server> {
    private final Config config;

    @Autowired
    public ServerObserver(Config config) {
        this.config = config;
    }

    @Override
    public void notify(Server subject) {
        if (config.isLogging()) {
            log.info("[Connect] " + subject.getAddress());
        }
    }
}
