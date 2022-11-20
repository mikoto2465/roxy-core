package net.mikoto.roxy.core.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mikoto.roxy.core.algorithm.Algorithm;
import net.mikoto.roxy.core.manager.AlgorithmManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstantiableAlgorithm {
    public String algorithmName;
    public InstantiableObject[] objectAlgorithmValues = null;
    public Object[] algorithmValues = null;

    @NotNull
    public static Algorithm<?> instance(@NotNull AlgorithmManager algorithmManager, @NotNull InstantiableAlgorithm instantiableAlgorithm) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object[] params;
        if (instantiableAlgorithm.getAlgorithmValues() != null) {
            params = instantiableAlgorithm.getAlgorithmValues();
        } else if (instantiableAlgorithm.getObjectAlgorithmValues() != null) {
            InstantiableObject[] instantiableObjects = instantiableAlgorithm.getObjectAlgorithmValues();

            params = new Object[instantiableObjects.length];
            for (int j = 0; j < params.length; j++) {
                params[j] = InstantiableObject.instance(instantiableObjects[j]);
            }
        } else {
            throw new RuntimeException();
        }

        return algorithmManager.createAlgorithmByName(instantiableAlgorithm.getAlgorithmName(), params);
    }
}
