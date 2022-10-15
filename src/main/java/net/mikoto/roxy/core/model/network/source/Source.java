package net.mikoto.roxy.core.model.network.source;

import java.util.Iterator;

/**
 * @author mikoto
 * @date 2022/6/18 17:00
 */
public interface Source<S> {
    /**
     * Add a source object.
     *
     * @param sourceObject The source object.
     */
    void addSourceObject(S sourceObject);

    /**
     * Get the source objects.
     *
     * @return The source objects.
     */
    Iterator<S> getIterator();

    /**
     * Get the source object.
     *
     * @return The source object.
     */
    S getSourceObject();
}
