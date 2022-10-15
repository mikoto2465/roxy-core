package net.mikoto.roxy.core.manager;

import net.mikoto.roxy.core.annotation.Algorithm;
import net.mikoto.roxy.core.model.Config;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static net.mikoto.roxy.core.util.ReflectionUtil.getClassesByAnnotation;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@Component("RoxyAlgorithmManager")
public class AlgorithmManager extends Manager<Class<?>> {
    private static final Map<String, Class<?>> ALGORITHM_MAP = new HashMap<>();

    @Autowired
    public AlgorithmManager(@NotNull Config config) {
        for (String algorithmPackage :
                config.getAlgorithmPackages()) {
            Set<Class<?>> algorithmClasses = getClassesByAnnotation(algorithmPackage, Algorithm.class);
            for (Class<?> algorithmClass :
                    algorithmClasses) {
                String algorithmName = algorithmClass.getAnnotation(Algorithm.class).value();
                ALGORITHM_MAP.put(algorithmName, algorithmClass);
                notifyObservers(algorithmClass);
            }
        }
    }

    @NotNull
    @SafeVarargs
    public final Object createAlgorithmByName(String algorithmName, @NotNull Pair<Class<?>, Object>... params) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> algorithmClass = ALGORITHM_MAP.get(algorithmName);

        Set<Class<?>> paramsTypes = new HashSet<>();
        Set<Object> paramsValues = new HashSet<>();
        for (Pair<Class<?>, Object> param : params){
            paramsTypes.add(param.getLeft());
            paramsValues.add(param.getRight());
        }

        Constructor<?> constructor = algorithmClass.getConstructor(paramsTypes.toArray(new Class<?>[0]));
        return constructor.newInstance(paramsValues.toArray(new Object[0]));
    }
}
