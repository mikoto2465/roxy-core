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

    /**
     * @return The class it promise response.
     */
    Class<?> resultClass();

    /**
     * @return The input it input.
     */
    Class<?>[] inputClasses() default {};
}
