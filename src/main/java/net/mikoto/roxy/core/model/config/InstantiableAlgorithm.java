package net.mikoto.roxy.core.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mikoto.roxy.core.algorithm.Algorithm;
import net.mikoto.roxy.core.manager.AlgorithmManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstantiableAlgorithm {
    public String algorithmName;
    public InstantiableObject[] algorithmParams;

    @NotNull
    public static Algorithm<?> instance(@NotNull AlgorithmManager algorithmManager, @NotNull InstantiableAlgorithm instantiableAlgorithm) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        InstantiableObject[] algorithmImplParams = instantiableAlgorithm.getAlgorithmParams();
        Object[] params = new Object[algorithmImplParams.length];
        for (int j = 0; j < params.length; j++) {
            params[j] = InstantiableObject.instance(algorithmImplParams[j]);
        }

        return algorithmManager.createAlgorithmByName(instantiableAlgorithm.getAlgorithmName(), params);
    }
}
