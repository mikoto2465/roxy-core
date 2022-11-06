package net.mikoto.roxy.core.manager;

import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.algorithm.Algorithm;
import net.mikoto.roxy.core.algorithm.ContainerType;
import net.mikoto.roxy.core.annotation.AlgorithmImpl;
import net.mikoto.roxy.core.annotation.AlgorithmInterface;
import net.mikoto.roxy.core.model.Config;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static net.mikoto.roxy.core.util.ReflectionUtil.getClassesByAnnotation;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@Component("RoxyAlgorithmManager")
@Log4j2
public class AlgorithmManager {
    private static final Map<String, AlgorithmInterface> ALGORITHM_INTERFACE_INFO = new HashMap<>();
    private static final Map<String, Class<?>> ALGORITHM_INTERFACE_CLASS = new HashMap<>();

    private static final Map<String, AlgorithmImpl> ALGORITHM_IMPL_INFO = new HashMap<>();
    private static final Map<String, Class<?>> ALGORITHM_IMPL_CLASS = new HashMap<>();

    @Autowired
    public AlgorithmManager(@NotNull Config config) {
        // Find algorithm interface class.
        for (String algorithmPackage :
                config.getAlgorithmPackages()) {
            Set<Class<?>> algorithmInterfaceClasses = getClassesByAnnotation(algorithmPackage, AlgorithmInterface.class);
            for (Class<?> algorithmInterfaceClass :
                    algorithmInterfaceClasses) {
                AlgorithmInterface annotation = algorithmInterfaceClass.getAnnotation(AlgorithmInterface.class);
                if (annotation != null) {
                    String algorithmInterfaceName = annotation.value();
                    ALGORITHM_INTERFACE_INFO.put(algorithmInterfaceName, annotation);
                    ALGORITHM_INTERFACE_CLASS.put(algorithmInterfaceName, algorithmInterfaceClass);
                    log.info("[Roxy] Found algorithm interface -> " + algorithmInterfaceName);
                }
            }

            Set<Class<?>> algorithmImplClasses = getClassesByAnnotation(algorithmPackage, AlgorithmImpl.class);
            for (Class<?> algorithmImplClass :
                    algorithmImplClasses) {
                AlgorithmImpl annotation = algorithmImplClass.getAnnotation(AlgorithmImpl.class);
                if (annotation != null) {
                    String algorithmInterfaceName = annotation.value();
                    ALGORITHM_IMPL_INFO.put(algorithmInterfaceName, annotation);
                    ALGORITHM_IMPL_CLASS.put(algorithmInterfaceName, algorithmImplClass);
                    log.info("[Roxy] Found algorithm impl -> " + algorithmInterfaceName);
                }
            }
        }
    }

    @NotNull
    public final Algorithm<?> createAlgorithmByName(String algorithmName, @NotNull Object... params) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> algorithmClass = ALGORITHM_IMPL_CLASS.get(algorithmName);
        AlgorithmImpl algorithm = ALGORITHM_IMPL_INFO.get(algorithmName);

        if (algorithm == null || algorithmClass == null) {
            throw new NullPointerException();
        }

        Class<?>[] classesArray = new Class[algorithm.constructorParamsClasses().length];
        if (algorithm.constructorParamsContainer() == ContainerType.NO_CONTAINER) {
            System.arraycopy(algorithm.constructorParamsClasses(), 0, classesArray, 0, algorithm.constructorParamsClasses().length);
        } else if (algorithm.constructorParamsContainer() == ContainerType.LIST) {
            classesArray[0] = List.class;
            params = new Object[]{Arrays.stream(params).toList()};
        }

        Constructor<?> constructor = algorithmClass.getConstructor(classesArray);
        Object o = constructor.newInstance(params);
        if (o instanceof Algorithm<?>) {
            return (Algorithm<?>) o;
        } else {
            throw new RuntimeException("Unsupported object");
        }
    }
}
