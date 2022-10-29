package net.mikoto.roxy.core;

import com.dtflys.forest.springboot.annotation.ForestScan;
import net.mikoto.roxy.core.algorithm.ServerAlgorithm;
import net.mikoto.roxy.core.algorithm.StringAlgorithm;
import net.mikoto.roxy.core.manager.AlgorithmManager;
import net.mikoto.roxy.core.manager.ModelManager;
import net.mikoto.roxy.core.model.RoxyModel;
import net.mikoto.roxy.core.model.network.server.CurrentWeightedHttpServer;
import net.mikoto.roxy.core.model.network.server.HttpServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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
    void algorithmManagerTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object staticStringAlgorithm = algorithmManager.createAlgorithmByName(
                "RoxyStaticStringAlgorithm",
                "test"
        );
        if (staticStringAlgorithm instanceof StringAlgorithm) {
            System.out.println(((StringAlgorithm) staticStringAlgorithm).run());
        }

        Object staticServerAlgorithm =
                algorithmManager.createAlgorithmByName(
                        "RoxyStaticServerAlgorithm",
                        new HttpServer("http://testaddress")
                );
        if (staticServerAlgorithm instanceof ServerAlgorithm) {
            System.out.println(((ServerAlgorithm) staticServerAlgorithm).run());
        }

        List<CurrentWeightedHttpServer> list = new ArrayList<>();
        list.add(new CurrentWeightedHttpServer("http://testaddress1", 1));
        list.add(new CurrentWeightedHttpServer("http://testaddress2", 2));
        list.add(new CurrentWeightedHttpServer("http://testaddress3", 3));
        list.add(new CurrentWeightedHttpServer("http://testaddress4", 4));
        list.add(new CurrentWeightedHttpServer("http://testaddress5", 5));

        Object SWSAlgorithm = algorithmManager.createAlgorithmByName("RoxySmoothWeightedServerAlgorithm", list);
        if (SWSAlgorithm instanceof ServerAlgorithm) {
            for (int i = 0; i < 20; i++) {
                System.out.println(((ServerAlgorithm) SWSAlgorithm).run().getAddress());
            }
        }
    }

    @Test
    void modelManagerTest() {
        Class<?> modelClass = modelManager.getRawModel("Artwork");
        System.out.println(modelClass.getSuperclass().getName());
    }
}
