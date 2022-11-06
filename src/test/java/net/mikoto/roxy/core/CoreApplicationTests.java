package net.mikoto.roxy.core;

import com.dtflys.forest.springboot.annotation.ForestScan;
import net.mikoto.roxy.core.algorithm.ObjectAlgorithm;
import net.mikoto.roxy.core.algorithm.ServerAlgorithm;
import net.mikoto.roxy.core.algorithm.impl.StaticObjectAlgorithm;
import net.mikoto.roxy.core.manager.AlgorithmManager;
import net.mikoto.roxy.core.manager.ConfigModelManager;
import net.mikoto.roxy.core.manager.DataModelManager;
import net.mikoto.roxy.core.manager.RoxyModelManager;
import net.mikoto.roxy.core.model.Config;
import net.mikoto.roxy.core.model.RoxyConfigModel;
import net.mikoto.roxy.core.model.RoxyModel;
import net.mikoto.roxy.core.model.network.server.HttpTarget;
import net.mikoto.roxy.core.scanner.ConfigScanner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

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
        RoxyConfigModel roxyConfigModel = configModelManager.getModelConfig("Artwork");
        assertEquals("Artwork", roxyConfigModel.getModelName());
    }

    @Test
    void modelManagerTest() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RoxyModel roxyModel = roxyModelManager.createModel("Artwork");
        assertEquals(new HttpTarget("https://www.pixiv.net", "/ajax/illust/${id}"), roxyModel.getResources().get("PixivOriginalResource").run());
        assertEquals("Artwork", roxyModel.getModelName());

        ServerAlgorithm pixivForwardResource = (ServerAlgorithm) roxyModel.getResources().get("PixivForwardResource");
        assertEquals(new HttpTarget("https://pixiv-forward-test-3.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());
        assertEquals(new HttpTarget("https://pixiv-forward-test-2.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());
        assertEquals(new HttpTarget("https://pixiv-forward-test-1.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());
        assertEquals(new HttpTarget("https://pixiv-forward-test-3.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());
        assertEquals(new HttpTarget("https://pixiv-forward-test-2.com", "/artwork/getInformation?artworkId=${id}"), pixivForwardResource.run());
    }

    @Test
    void HttpTargetModelTest() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        HttpTarget httpTarget = new HttpTarget("https://www.pixiv.net", "/ajax/illust/${id}");
        Class<?> dataModelClass = dataModelManager.getDataModelClass("Artwork");
        Object dataModel = dataModelClass.getConstructor().newInstance();
        Field artworkIdField = dataModelClass.getDeclaredField("artworkId");
        artworkIdField.setAccessible(true);
        artworkIdField.set(dataModel, 1);
        assertEquals("https://www.pixiv.net/ajax/illust/1", httpTarget.getFullAddress(dataModelClass, dataModel));
    }
}
