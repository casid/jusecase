package org.jusecase.executors;

import org.jusecase.Usecase;
import org.jusecase.UsecaseExecutorException;
import org.jusecase.VoidUsecase;
import org.jusecase.util.GenericTypeResolver;

public class UsecaseRequestResolver {
    public Class<?> getRequestClass(Class<?> usecaseClass) {
        Class<?> requestClass = resolveTypeArguments(usecaseClass);
        if (requestClass == null) {
            throw new UsecaseExecutorException("Could not resolve usecase request type for class '" + usecaseClass.getName() + "'. Hint: The concrete usecase class is required to resolve the request class.");
        }

        return requestClass;
    }

    private Class<?> resolveTypeArguments(Class<?> usecaseClass) {
        if (Usecase.class.isAssignableFrom(usecaseClass)) {
            return GenericTypeResolver.resolve(Usecase.class, usecaseClass, 0);
        } else {
            return GenericTypeResolver.resolve(VoidUsecase.class, usecaseClass, 0);
        }
    }
}
