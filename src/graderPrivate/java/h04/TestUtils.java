package h04;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestUtils {

    public static boolean methodSignatureEquals(Method method, String methodName, Class<?>... parameterTypes) {
        return method.getName().equals(methodName) && Arrays.equals(method.getParameterTypes(), parameterTypes);
    }
}
