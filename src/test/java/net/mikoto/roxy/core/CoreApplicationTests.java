package net.mikoto.roxy.core;

import com.dtflys.forest.springboot.annotation.ForestScan;
import net.mikoto.roxy.core.algorithm.*;
import net.mikoto.roxy.core.algorithm.impl.StaticObjectAlgorithm;
import net.mikoto.roxy.core.manager.AlgorithmManager;
import net.mikoto.roxy.core.manager.ConfigModelManager;
import net.mikoto.roxy.core.manager.DataModelManager;
import net.mikoto.roxy.core.manager.RoxyModelManager;
import net.mikoto.roxy.core.model.Config;
import net.mikoto.roxy.core.model.Resource;
import net.mikoto.roxy.core.model.config.RoxyModelConfig;
import net.mikoto.roxy.core.model.RoxyModel;
import net.mikoto.roxy.core.model.network.resource.HttpTarget;
import net.mikoto.roxy.core.scanner.ConfigScanner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = {
                AlgorithmManager.class,
                DataModelManager.class,
                ConfigModelManager.class,
                RoxyModelManager.class,
                ConfigScanner.class,
                Config.class
        })
@ComponentScan("net.mikoto.roxy")
@ForestScan("net.mikoto.roxy")
class CoreApplicationTests {

    private final AlgorithmManager algorithmManager;
    private final DataModelManager dataModelManager;
    private final ConfigModelManager configModelManager;
    private final RoxyModelManager roxyModelManager;
    private final Config config;

    @Autowired
    CoreApplicationTests(AlgorithmManager algorithmManager, DataModelManager dataModelManager, ConfigModelManager configModelManager, RoxyModelManager roxyModelManager, Config config) {
        this.algorithmManager = algorithmManager;
        this.dataModelManager = dataModelManager;
        this.configModelManager = configModelManager;
        this.roxyModelManager = roxyModelManager;
        this.config = config;
    }

    @Test
    void algorithmManagerTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object staticObjectAlgorithm = algorithmManager.createAlgorithmByName(
                "RoxyStaticObjectAlgorithm",
                "test"
        );
        assertInstanceOf(StaticObjectAlgorithm.class, staticObjectAlgorithm);
        assertEquals("test", ((ObjectAlgorithm) staticObjectAlgorithm).run());
    }

    @Test
    void shardableAlgorithmTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Algorithm<?> algorithm = algorithmManager.createAlgorithmByName("RoxyIntegerIncrementAlgorithm", 1, 12);
        if (algorithm instanceof ShardableAlgorithm<?>) {
            Algorithm<?>[] algorithms = ((ShardableAlgorithm<?>) algorithm).shard(10);
            System.out.println(Arrays.toString(algorithms));
        }
    }

    @Test
    void modelConfigManagerTest() {
        RoxyModelConfig roxyModelConfig = configModelManager.get("Artwork");
        assertEquals("Artwork", roxyModelConfig.getModelName());
    }

    @Test
    void modelManagerTest() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RoxyModel roxyModel = roxyModelManager.createModel("Artwork");
        assertEquals(new HttpTarget("https://www.pixiv.net", "/ajax/illust/${id}"), roxyModel.getResources().get("PixivOriginalResource").getResourceAlgorithm().run());
        assertEquals("Artwork", roxyModel.getModelName());

        Algorithm<?> pixivForwardResource = roxyModel.getResources().get("PixivForwardResource").getResourceAlgorithm();
        assertEquals(new HttpTarget("https://pixiv-forward-test-3.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());
        assertEquals(new HttpTarget("https://pixiv-forward-test-2.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());
        assertEquals(new HttpTarget("https://pixiv-forward-test-1.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());
        assertEquals(new HttpTarget("https://pixiv-forward-test-3.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());
        assertEquals(new HttpTarget("https://pixiv-forward-test-2.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());

        for (int i = 0; i < 10; i++) {
            Resource resource = (Resource) roxyModel.getResourcesAlgorithm().run();
            HttpTarget httpTarget = (HttpTarget) resource.getResourceAlgorithm().run();
            System.out.println(httpTarget);
        }
    }

    @Test
    void HttpTargetModelTest() {
        HttpTarget httpTarget = new HttpTarget("https://www.pixiv.net", "/ajax/illust/${id}");
        Map<Object, Object> valueMap = new HashMap<>();
        valueMap.put("id", 1);
        assertEquals("https://www.pixiv.net/ajax/illust/1", httpTarget.getFullAddress(valueMap));
    }

    @Test
    void RoxyPatcherTest() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RoxyModel roxyModel = roxyModelManager.createModel("Artwork");
        RoxyPatcher roxyPatcher = new RoxyPatcher(roxyModel, config);

        roxyPatcher.start();
    }
}
