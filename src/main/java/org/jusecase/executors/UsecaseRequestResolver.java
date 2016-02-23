package org.jusecase.executors;

import net.jodah.typetools.TypeResolver;
import org.jusecase.Usecase;
import org.jusecase.UsecaseExecutorException;

public class UsecaseRequestResolver {
    public Class<?> getRequestClass(Class<?> usecaseClass) {
        Class<?>[] typeArgs = TypeResolver.resolveRawArguments(Usecase.class, usecaseClass);
        Class<?> requestClass = typeArgs[0];
        if (requestClass == TypeResolver.Unknown.class) {
            throw new UsecaseExecutorException("Could not resolve usecase request type for class '" + usecaseClass.getName() + "'. Hint: The concrete usecase class is required to resolve the request class.");
        }

        return requestClass;
    }
}
