package net.mikoto.roxy.core.model;

import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import lombok.Getter;
import lombok.Setter;
import net.mikoto.roxy.core.model.network.HttpHandler;
import net.mikoto.roxy.core.model.network.resource.HttpTarget;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@Configuration("RoxyConfig")
@Service
@ConfigurationProperties(prefix = "roxy")
@Getter
@Setter
public class Config {
    private boolean isLogging = true;
    private String[] observers = new String[]{"RoxyServerObserver"};
    private String configPath = "/config";
    private String fileSuffix = ".json";
    private String modelPackage = "net.mikoto.roxy.core.model";
    private String modelSuffix = ".model" + fileSuffix;
    private String configSuffix = ".config" + fileSuffix;
    private String[] algorithmPackages = new String[]{"net.mikoto.roxy.core.algorithm"};
    private HttpHandler<?, ?, ?> httpHandler = new HttpHandler<ForestRequest<?>, String, HttpTarget>() {
        @Override
        public String run(@NotNull Object httpRequest) {
            if (httpRequest instanceof ForestRequest<?>) {
                return ((ForestRequest<?>) httpRequest).execute(ForestResponse.class).getContent();
            } else {
                throw new RuntimeException();
            }
        }

        @NotNull
        @Override
        public ForestRequest<?> getRequest(Object httpTarget, @NotNull Object... objects) {
            ForestRequest<?> forestRequest = Forest.request();
            if (objects[0] instanceof Map<?,?> && httpTarget instanceof HttpTarget) {
                forestRequest.url(((HttpTarget) httpTarget).getFullAddress((Map<?, ?>) objects[0]));
                return forestRequest;
            } else {
                throw new IllegalArgumentException();
            }
        }
    };


}
