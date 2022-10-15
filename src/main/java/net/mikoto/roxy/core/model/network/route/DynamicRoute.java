package net.mikoto.roxy.core.model.network.route;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
public class DynamicRoute implements Route {
    private final String baseRoute;

    /**
     * @param baseRoute The base route can use {$0}, {$1}, {$2} and so on. To use in getRoute(...)
     */
    public DynamicRoute(String baseRoute) {
        this.baseRoute = baseRoute;
    }

    @Override
    public String getRoute(String... params) {
        String resultString = baseRoute;
        for (int i = 0; i < params.length; i++) {
            resultString = resultString.replace("{$" + i + "}", params[i]);
        }
        return resultString;
    }
}
