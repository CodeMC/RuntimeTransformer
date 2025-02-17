package net.hypercubemc.runtimetransformer.agent;

import net.hypercubemc.runtimetransformer.annotation.CallParameters;

import java.lang.reflect.Method;

public class SpecialInvocation {

    private Method method;

    private CallParameters callParameters;

    public SpecialInvocation(Method method) {
        this.method = method;
        this.callParameters = method.getAnnotation(CallParameters.class);
    }

    public Method getMethod() {
        return method;
    }

    public CallParameters getCallParameters() {
        return callParameters;
    }

}
