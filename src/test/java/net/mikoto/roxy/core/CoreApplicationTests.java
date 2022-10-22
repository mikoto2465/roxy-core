package net.mikoto.roxy.core;

import com.dtflys.forest.springboot.annotation.ForestScan;
import net.mikoto.roxy.core.manager.AlgorithmManager;
import net.mikoto.roxy.core.manager.ModelManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan("net.mikoto.roxy")
@ForestScan("net.mikoto.roxy")
@SpringBootApplication
class CoreApplicationTests {

    private final AlgorithmManager algorithmManager;
    private final ModelManager modelManager;

    @Autowired
    CoreApplicationTests(AlgorithmManager algorithmManager, ModelManager modelManager) {
        this.algorithmManager = algorithmManager;
        this.modelManager = modelManager;
    }

    @Test
    void contextLoads() {
        algorithmManager.createAlgorithmByName()
    }

}
