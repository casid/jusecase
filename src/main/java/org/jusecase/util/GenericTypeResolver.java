package org.jusecase.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericTypeResolver {
    public static Class<?> resolve(Type type, Class<?> usecaseClass, int index) {
        Type[] genericInterfaces = usecaseClass.getGenericInterfaces();

        for (Type genericInterface : genericInterfaces) {
            if (ParameterizedType.class.isAssignableFrom(genericInterface.getClass())) {
                ParameterizedType parameterizedType = (ParameterizedType)genericInterface;
                if (parameterizedType.getRawType() == type) {
                    return (Class<?>)(parameterizedType.getActualTypeArguments()[index]);
                }
            }
        }

        ParameterizedType parameterizedType = (ParameterizedType)usecaseClass.getGenericSuperclass();
        if (parameterizedType != null) {
            return (Class<?>)(parameterizedType.getActualTypeArguments()[index]);
        }

        return null;
    }
}
