package net.mikoto.roxy.core.util;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author mikoto
 */
public final class ReflectionUtil {
    /**
     * Get the classes by the annotation.
     *
     * @param packageName The package name.
     * @param annotationClass The annotation class.
     * @return The classes.
     */
    public static Set<Class<?>> getClassesByAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
        Reflections reflection = new Reflections(packageName);
        return reflection.getTypesAnnotatedWith(annotationClass);
    }
}
