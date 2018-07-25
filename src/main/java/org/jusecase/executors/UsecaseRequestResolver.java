package org.jusecase.executors;

import net.jodah.typetools.TypeResolver;
import org.jusecase.Usecase;
import org.jusecase.UsecaseExecutorException;
import org.jusecase.VoidUsecase;

public class UsecaseRequestResolver {
    public Class<?> getRequestClass(Class<?> usecaseClass) {
        Class<?>[] typeArguments = resolveTypeArguments(usecaseClass);
        Class<?> requestClass = typeArguments[0];
        if (requestClass == TypeResolver.Unknown.class) {
            throw new UsecaseExecutorException("Could not resolve usecase request type for class '" + usecaseClass.getName() + "'. Hint: The concrete usecase class is required to resolve the request class.");
        }

        return requestClass;
    }

    private Class<?>[] resolveTypeArguments(Class<?> usecaseClass) {
        if (Usecase.class.isAssignableFrom(usecaseClass)) {
            return TypeResolver.resolveRawArguments(Usecase.class, usecaseClass);
        } else {
            return TypeResolver.resolveRawArguments(VoidUsecase.class, usecaseClass);
        }
    }
}
