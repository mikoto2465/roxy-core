package net.mikoto.roxy.core;

import com.dtflys.forest.springboot.annotation.ForestScan;
import net.mikoto.roxy.core.strategy.*;
import net.mikoto.roxy.core.strategy.impl.StaticObjectStrategy;
import net.mikoto.roxy.core.manager.*;
import net.mikoto.roxy.core.model.Config;
import net.mikoto.roxy.core.model.Resource;
import net.mikoto.roxy.core.model.config.RoxyModelConfig;
import net.mikoto.roxy.core.model.RoxyModel;
import net.mikoto.roxy.core.model.network.resource.HttpTarget;
import net.mikoto.roxy.core.observer.impl.ProgressiveObserver;
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
                StrategyManager.class,
                DataModelManager.class,
                RoxyModelConfigManager.class,
                RoxyModelManager.class,
                ConfigScanner.class,
                Config.class
        })
@ComponentScan("net.mikoto.roxy")
@ForestScan("net.mikoto.roxy")
class CoreApplicationTests {

    private final StrategyManager strategyManager;
    private final DataModelManager dataModelManager;
    private final RoxyModelConfigManager roxyModelConfigManager;
    private final RoxyModelManager roxyModelManager;
    private final RoxyPatcherManager roxyPatcherManager;
    private final Config config;

    @Autowired
    CoreApplicationTests(StrategyManager strategyManager, DataModelManager dataModelManager, RoxyModelConfigManager roxyModelConfigManager, RoxyModelManager roxyModelManager, RoxyPatcherManager roxyPatcherManager, Config config) {
        this.strategyManager = strategyManager;
        this.dataModelManager = dataModelManager;
        this.roxyModelConfigManager = roxyModelConfigManager;
        this.roxyModelManager = roxyModelManager;
        this.roxyPatcherManager = roxyPatcherManager;
        this.config = config;
    }

    @Test
    void algorithmManagerTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object staticObjectAlgorithm = strategyManager.createAlgorithmByName(
                "RoxyStaticObjectAlgorithm",
                "test"
        );
        assertInstanceOf(StaticObjectStrategy.class, staticObjectAlgorithm);
        assertEquals("test", ((ObjectStrategy) staticObjectAlgorithm).run());
    }

    @Test
    void shardableAlgorithmTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Strategy<?> strategy = strategyManager.createAlgorithmByName("RoxyIntegerIncrementAlgorithm", 1, 12);
        if (strategy instanceof ShardableStrategy<?>) {
            Strategy<?>[] strategies = ((ShardableStrategy<?>) strategy).shard(10);
            System.out.println(Arrays.toString(strategies));
        }
    }

    @Test
    void modelConfigManagerTest() {
        RoxyModelConfig roxyModelConfig = roxyModelConfigManager.get("Artwork");
        assertEquals("Artwork", roxyModelConfig.getModelName());
    }

    @Test
    void modelManagerTest() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RoxyModel roxyModel = roxyModelManager.createModel("Artwork");
        assertEquals(new HttpTarget("https://www.pixiv.net", "/ajax/illust/${id}"), roxyModel.getResources().get("PixivOriginalResource").getResourceStrategy().run());
        assertEquals("Artwork", roxyModel.getModelName());

        Strategy<?> pixivForwardResource = roxyModel.getResources().get("PixivForwardResource").getResourceStrategy();
        assertEquals(new HttpTarget("http://localhost:8081", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());

        for (int i = 0; i < 10; i++) {
            Resource resource = (Resource) roxyModel.getResourcesStrategy().run();
            HttpTarget httpTarget = (HttpTarget) resource.getResourceStrategy().run();
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
    void RoxyPatcherTest() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        RoxyModel roxyModel = roxyModelManager.createModel("Artwork");
        RoxyPatcher roxyPatcher = roxyPatcherManager.createPatcher(roxyModel);
        roxyPatcher.start();
        while (roxyPatcher.getThreadCount() != 0) {
            ((ProgressiveObserver) (roxyModel.getTask().getTaskObservers()[0])).getPercent();
            Thread.sleep(500);
        }
    }
}
