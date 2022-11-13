package net.mikoto.roxy.core;

import com.dtflys.forest.springboot.annotation.ForestScan;
import net.mikoto.roxy.core.algorithm.ObjectAlgorithm;
import net.mikoto.roxy.core.algorithm.HttpTargetAlgorithm;
import net.mikoto.roxy.core.algorithm.impl.StaticObjectAlgorithm;
import net.mikoto.roxy.core.manager.AlgorithmManager;
import net.mikoto.roxy.core.manager.ConfigModelManager;
import net.mikoto.roxy.core.manager.DataModelManager;
import net.mikoto.roxy.core.manager.RoxyModelManager;
import net.mikoto.roxy.core.model.Config;
import net.mikoto.roxy.core.model.RoxyConfigModel;
import net.mikoto.roxy.core.model.RoxyModel;
import net.mikoto.roxy.core.model.network.resource.HttpTarget;
import net.mikoto.roxy.core.scanner.ConfigScanner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.InvocationTargetException;
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

    @Autowired
    CoreApplicationTests(AlgorithmManager algorithmManager, DataModelManager dataModelManager, ConfigModelManager configModelManager, RoxyModelManager roxyModelManager) {
        this.algorithmManager = algorithmManager;
        this.dataModelManager = dataModelManager;
        this.configModelManager = configModelManager;
        this.roxyModelManager = roxyModelManager;
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
    void modelConfigManagerTest() {
        RoxyConfigModel roxyConfigModel = configModelManager.get("Artwork");
        assertEquals("Artwork", roxyConfigModel.getModelName());
    }

    @Test
    void modelManagerTest() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RoxyModel roxyModel = roxyModelManager.createModel("Artwork");
        assertEquals(new HttpTarget("https://www.pixiv.net", "/ajax/illust/${id}"), roxyModel.getResources().get("PixivOriginalResource").getResourceAlgorithm().run());
        assertEquals("Artwork", roxyModel.getModelName());

        HttpTargetAlgorithm pixivForwardResource = (HttpTargetAlgorithm) roxyModel.getResources().get("PixivForwardResource").getResourceAlgorithm();
        assertEquals(new HttpTarget("https://pixiv-forward-test-3.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());
        assertEquals(new HttpTarget("https://pixiv-forward-test-2.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());
        assertEquals(new HttpTarget("https://pixiv-forward-test-1.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());
        assertEquals(new HttpTarget("https://pixiv-forward-test-3.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());
        assertEquals(new HttpTarget("https://pixiv-forward-test-2.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());
    }

    @Test
    void HttpTargetModelTest() {
        HttpTarget httpTarget = new HttpTarget("https://www.pixiv.net", "/ajax/illust/${id}");
        Map<Object, Object> valueMap = new HashMap<>();
        valueMap.put("id", 1);
        assertEquals("https://www.pixiv.net/ajax/illust/1", httpTarget.getFullAddress(valueMap));
    }
}
