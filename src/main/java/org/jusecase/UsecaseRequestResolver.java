package org.jusecase;

import net.jodah.typetools.TypeResolver;

public class UsecaseRequestResolver {
    public Class<?> getRequestClass(Class<?> usecaseClass) {
        Class<?>[] typeArgs = TypeResolver.resolveRawArguments(Usecase.class, usecaseClass);
        Class<?> requestClass = typeArgs[0];
        if (requestClass == TypeResolver.Unknown.class) {
            throw new UsecaseExecutorException("Could not resolve usecase request type for class '" + usecaseClass.getName() + "'");
        }

        return requestClass;
    }
}
