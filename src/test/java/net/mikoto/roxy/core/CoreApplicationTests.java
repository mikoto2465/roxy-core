package net.mikoto.roxy.core;

import com.dtflys.forest.springboot.annotation.ForestScan;
import net.mikoto.roxy.core.algorithm.StringAlgorithm;
import net.mikoto.roxy.core.manager.AlgorithmManager;
import net.mikoto.roxy.core.manager.ModelManager;
import net.mikoto.roxy.core.model.network.server.HttpServer;
import net.mikoto.roxy.core.model.network.server.Server;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.InvocationTargetException;
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
    void contextLoads() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object staticStringAlgorithm = algorithmManager.createAlgorithmByName("RoxyStaticStringAlgorithm", "test");
        if (staticStringAlgorithm instanceof StringAlgorithm) {
            System.out.println(((StringAlgorithm) staticStringAlgorithm).run());
        }

        Object staticServerAlgorithm = algorithmManager.createAlgorithmByName("RoxyStaticServerAlgorithm", new HttpServer("http://testaddress"));
        if (staticServerAlgorithm instanceof Server) {
            System.out.println(((StringAlgorithm) staticServerAlgorithm).run());
        }

        List<>

        Object SWSAlgorithm = algorithmManager.createAlgorithmByName("RoxySmoothWeightedServerAlgorithm", new HttpServer("http://testaddress"));
        if (SWSAlgorithm instanceof Server) {
            System.out.println(((StringAlgorithm) SWSAlgorithm).run());
        }
    }

}
