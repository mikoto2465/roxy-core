package net.mikoto.roxy.core.model.network.source;

import net.mikoto.roxy.core.model.network.weight.CurrentWeighted;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author mikoto
 * @date 2022/6/12 3:47
 */
public class SmoothWeightedSource<S extends CurrentWeighted> implements Source<S> {
    private final Set<S> SOURCE_OBJECT_SET = new HashSet<>();

    /**
     * Add a source object.
     *
     * @param sourceObject The source object.
     */
    @Override
    public void addSourceObject(S sourceObject) {
        SOURCE_OBJECT_SET.add(sourceObject);
    }

    /**
     * Get the source objects.
     *
     * @return The source objects.
     */
    @Override
    public Iterator<S> getIterator() {
        return SOURCE_OBJECT_SET.iterator();
    }

    /**
     * Get the source object.
     * The realization of the Smooth Weighted Round Robin Algorithm.
     * If the source object set is null it will return null!
     *
     * @return The source.
     */
    @Override
    public S getSourceObject() {
        if (!SOURCE_OBJECT_SET.isEmpty()) {
            Iterator<S> sourceSetIterator = SOURCE_OBJECT_SET.iterator();

            S resultSource = sourceSetIterator.next();
            resultSource.setCurrentWeight(resultSource.getWeight() + resultSource.getCurrentWeight());
            int currentWeightSum = resultSource.getCurrentWeight();

            while (sourceSetIterator.hasNext()) {
                S source = sourceSetIterator.next();
                source.setCurrentWeight(source.getWeight() + source.getCurrentWeight());
                if (source.getCurrentWeight() > resultSource.getCurrentWeight()) {
                    resultSource = source;
                }
                currentWeightSum += source.getCurrentWeight();
            }

            resultSource.setCurrentWeight(resultSource.getCurrentWeight() - currentWeightSum);

            return resultSource;
        } else {
            return null;
        }
    }
}
