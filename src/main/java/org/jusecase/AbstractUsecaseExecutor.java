package org.jusecase;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractUsecaseExecutor implements UsecaseExecutor {
    private Map<Class<?>, Object> usecases = new HashMap<Class<?>, Object>();
    private UsecaseRequestResolver requestResolver = new UsecaseRequestResolver();

    public <Request, Response> Response execute(Request request) {
        if (request == null) {
            throw new UsecaseExecutorException("Request must not be null.");
        }

        Usecase<Request, Response> usecase = getUsecase(request.getClass());
        return usecase.execute(request);
    }

    @SuppressWarnings("unchecked")
    public <Request, Response> Usecase<Request, Response> getUsecase(Class<?> requestClass) {
        Object usecase = usecases.get(requestClass);
        return (Usecase<Request, Response>)resolveUsecase(usecase);
    }

    protected void addUsecase(Class<?> requestClass, Object usecase) {
        if (usecases.containsKey(requestClass)) {
            throw new UsecaseExecutorException("Request '" + requestClass.getName() + "' is already handled by a usecase.");
        }
        usecases.put(requestClass, usecase);
    }

    protected abstract Object resolveUsecase(Object usecase);

    protected Class<?> getRequestClass(Class<?> usecaseClass) {
        return requestResolver.getRequestClass(usecaseClass);
    }
}
