package net.mikoto.roxy.core.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@Configuration
@Service
@ConfigurationProperties(prefix = "roxy")
@Getter
@Setter
public class Config {
    private boolean isLogging = true;
    private String[] observers = new String[]{"RoxyServerObserver"};
    private String modelPath = "/model";
    private String[] algorithmPackages = new String[]{"net.mikoto.roxy.core.algorithm"};
    private String modelSuffix = ".model.json";
}
