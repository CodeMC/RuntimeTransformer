package net.hypercubemc.runtimetransformer.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Yamakaja on 19.05.17.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {

    /**
     * @return How to inject the code
     */
    InjectionType value() default InjectionType.OVERRIDE;

}
