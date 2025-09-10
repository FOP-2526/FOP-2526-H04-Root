package h04;

import org.tudalgo.algoutils.tutor.general.reflections.FieldLink;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestUtils {

    public static boolean methodSignatureEquals(Method method, String methodName, Class<?>... parameterTypes) {
        return method.getName().equals(methodName) && Arrays.equals(method.getParameterTypes(), parameterTypes);
    }

    public static boolean isNumericType(Class<?> clazz) {
        return
            clazz == byte.class || clazz == Byte.class ||
            clazz == short.class || clazz == Short.class ||
            clazz == int.class || clazz == Integer.class ||
            clazz == long.class || clazz == Long.class ||
            clazz == float.class || clazz == Float.class ||
            clazz == double.class || clazz == Double.class;
    }

    public static Number toFittingType(Class<?> fieldType, Integer value) {
        if (fieldType == byte.class || fieldType == Byte.class) {
            return value.byteValue();
        } else if (fieldType == short.class || fieldType == Short.class) {
            return value.shortValue();
        } else if (fieldType == int.class || fieldType == Integer.class) {
            return value;
        } else if (fieldType == long.class || fieldType == Long.class) {
            return value.longValue();
        } else if (fieldType == float.class || fieldType == Float.class) {
            return value.floatValue();
        } else if (fieldType == double.class || fieldType == Double.class) {
            return value.doubleValue();
        } else {
            throw new ClassCastException("Attempted to convert value to a fitting numeric type, but none matched");
        }
    }

    public static void setNumericField(FieldLink fieldLink, Object instance, Integer value) {
        fieldLink.set(instance, toFittingType(fieldLink.reflection().getType(), value));
    }
}
