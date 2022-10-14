package net.mikoto.roxy.core.observer.impl;

import net.mikoto.roxy.core.observer.Observer;
import net.mikoto.roxy.core.server.Server;
import org.springframework.stereotype.Component;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@Component("RoxyServerObject")
public class ServerObserver implements Observer<Server> {

    @Override
    public void notify(Server subject) {

    }
}
