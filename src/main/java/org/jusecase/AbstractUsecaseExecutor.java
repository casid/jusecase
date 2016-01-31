package org.jusecase;

import net.jodah.typetools.TypeResolver;
import org.jusecase.manual.Factory;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractUsecaseExecutor implements UsecaseExecutor {
    private Map<Class<?>, Object> usecases = new HashMap<Class<?>, Object>();

    protected void addUsecase(Class<?> requestClass, Object usecase) {
        usecases.put(requestClass, usecase);
    }

    @SuppressWarnings("unchecked")
    protected  <Request, Response> Usecase<Request, Response> getUsecase(Request request) {
        Object usecase = usecases.get(request.getClass());
        return (Usecase<Request, Response>)resolveUsecase(usecase);
    }

    protected abstract Object resolveUsecase(Object usecase);

    protected Class<?> getRequestClass(Class<?> usecaseClass) {
        Class<?>[] typeArgs = TypeResolver.resolveRawArguments(Usecase.class, usecaseClass);
        Class<?> requestClass = typeArgs[0];
        if (requestClass == TypeResolver.Unknown.class) {
            throw new UsecaseExecutorException("Could not resolve usecase request type for class '" + usecaseClass.getName() + "'");
        }

        return requestClass;
    }
}
