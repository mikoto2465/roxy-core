package net.mikoto.roxy.core.source;

import java.util.Iterator;
import java.util.Set;

/**
 * @author mikoto
 * @date 2022/6/18 17:11
 */
public class StaticSource<S> implements Source<S> {
    private S sourceObject;

    /**
     * Add a source object.
     *
     * @param sourceObject The source object.
     */
    @Override
    public void addSourceObject(S sourceObject) {
        this.sourceObject = sourceObject;
    }

    /**
     * Get the source objects.
     * It will always return null, because there is only one object.
     *
     * @return The source objects.
     */
    @Override
    public Iterator<S> getIterator() {
        return null;
    }

    /**
     * Get the source object.
     *
     * @return The source object.
     */
    @Override
    public S getSourceObject() {
        return sourceObject;
    }
}
