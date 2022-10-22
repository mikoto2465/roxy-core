package net.mikoto.roxy.core.annotation;

import java.lang.annotation.*;

/**
 * @author mikoto
 * @date 2022/10/22
 * Create for core
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AlgorithmInterface {
    /**
     * @return The name of the interface
     */
    String value();

    Class<?> resultClass();

    Class<?>[] inputClasses() default {};
}
