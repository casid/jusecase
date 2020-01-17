package org.jusecase.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericTypeResolver {
    public static Class<?> resolve(Type type, Class<?> clazz, int index) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();

        for (Type genericInterface : genericInterfaces) {
            if (ParameterizedType.class.isAssignableFrom(genericInterface.getClass())) {
                ParameterizedType parameterizedType = (ParameterizedType)genericInterface;
                if (parameterizedType.getRawType() == type) {
                    return (Class<?>)(parameterizedType.getActualTypeArguments()[index]);
                }
            }
        }

        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            return (Class<?>) (parameterizedType.getActualTypeArguments()[index]);
        } else if (genericSuperclass instanceof Class<?>) {
            return resolve(type, (Class<?>)genericSuperclass, index);
        }

        return null;
    }
}
