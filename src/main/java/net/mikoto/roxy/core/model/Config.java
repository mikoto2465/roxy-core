package net.mikoto.roxy.core.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

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

}
