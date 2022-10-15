package net.mikoto.roxy.core.model.network.route;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
public record StaticRoute(String route) implements Route {
    @Override
    public String getRoute(String... paras) {
        return route;
    }
}
