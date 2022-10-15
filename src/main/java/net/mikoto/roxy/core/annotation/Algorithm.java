package net.mikoto.roxy.core.annotation;

import java.lang.annotation.*;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Algorithm {
    String value();
}
