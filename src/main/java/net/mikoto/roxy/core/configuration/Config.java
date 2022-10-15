package net.mikoto.roxy.core.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@Configuration
@ConfigurationProperties(prefix = "roxy")
@Getter
@Setter
public class Config {
    private boolean isLogging = true;
    private String[] observers = new String[]{"RoxyServerObserver"};
    private String modelPath = "/model";
    private String[] algorithmPackages = new String[]{"net.mikoto.roxy.core.algorithm"};
}
