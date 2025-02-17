package net.hypercubemc.runtimetransformer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Yamakaja on 3/3/18.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TransformByName {

    /**
     * @return The string representation of the transformation target as returned by {@link Class#getName()}
     */
    String value();

}
