package net.hypercubemc.runtimetransformer.util;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by Yamakaja on 19.05.17.
 */
public class MethodUtils {

    private MethodUtils() {
    }

    public static String getSignature(Method method) {
        StringBuilder signature = new StringBuilder();

        signature.append('(');

        for (Parameter parameter : method.getParameters()) {
            signature.append(translateTypeToInternal(parameter.getType()));
        }

        signature.append(')');

        signature.append(translateTypeToInternal(method.getReturnType()));

        return signature.toString();
    }

    public static String translateTypeToInternal(Class<?> type) {
        if(type.isArray())
            return type.getName();

        switch(type.getName()) {
            case "boolean":
                return "Z";
            case "byte":
                return "B";
            case "char":
                return "C";
            case "double":
                return "D";
            case "float":
                return "F";
            case "int":
                return "I";
            case "long":
                return "J";
            case "short":
                return "S";
            case "void":
                return "V";
            default:
                return "L" + type.getName().replace('.','/') + ";";
        }
    }

}
