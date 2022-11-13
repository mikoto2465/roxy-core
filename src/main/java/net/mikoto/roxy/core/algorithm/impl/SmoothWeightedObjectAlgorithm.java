package net.mikoto.roxy.core.algorithm.impl;

import net.mikoto.roxy.core.algorithm.ContainerType;
import net.mikoto.roxy.core.algorithm.HttpTargetAlgorithm;
import net.mikoto.roxy.core.algorithm.ObjectAlgorithm;
import net.mikoto.roxy.core.annotation.AlgorithmImpl;
import net.mikoto.roxy.core.model.network.resource.HttpTarget;
import net.mikoto.roxy.core.model.network.weight.CurrentWeighted;

import java.util.Iterator;
import java.util.List;

/**
 * @author mikoto
 * @date 2022/10/22
 * Create for core
 */
@AlgorithmImpl(
        value = "RoxySmoothWeightedObjectAlgorithm",
        constructorParamsContainer = ContainerType.LIST
)
public class SmoothWeightedObjectAlgorithm implements ObjectAlgorithm {
    private final List<? extends CurrentWeighted> SERVER_LIST;

    public SmoothWeightedObjectAlgorithm(List<? extends CurrentWeighted> list) {
        if (list != null && !list.isEmpty() && list.get(0) != null) {
            this.SERVER_LIST = list;
        } else {
            throw new RuntimeException("Unknown type");
        }
    }

    @Override
    public CurrentWeighted run(Object... objects) {
        if (!SERVER_LIST.isEmpty()) {
            Iterator<? extends CurrentWeighted> sourceSetIterator = SERVER_LIST.iterator();

            CurrentWeighted result = sourceSetIterator.next();
            result.setCurrentWeight(result.getWeight() + result.getCurrentWeight());
            int currentWeightSum = result.getCurrentWeight();

            while (sourceSetIterator.hasNext()) {
                CurrentWeighted server = sourceSetIterator.next();
                server.setCurrentWeight(server.getWeight() + server.getCurrentWeight());
                if (server.getCurrentWeight() > result.getCurrentWeight()) {
                    result = server;
                }
                currentWeightSum += server.getCurrentWeight();
            }

            result.setCurrentWeight(result.getCurrentWeight() - currentWeightSum);

            return result;
        } else {
            return null;
        }
    }
}
