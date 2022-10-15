package net.mikoto.roxy.core.model.network.route;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 *
 * The route of the request.
 */
public interface Route {
    String getRoute(String... paras);
}
