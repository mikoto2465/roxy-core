package net.mikoto.roxy.core.annotation;

import net.mikoto.roxy.core.strategy.ContainerType;

import java.lang.annotation.*;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 *
 * This annotation help you make an algorithm class
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AlgorithmImpl {
    /**
     * @return The name of the algorithm.
     */
    String value();

    /**
     * @return The params of the constructor.
     */
    Class<?>[] constructorParamsClasses() default {String.class};

    /**
     * @return The special object type container.(Auto create)
     */
    ContainerType constructorParamsContainer() default ContainerType.NO_CONTAINER;
}
