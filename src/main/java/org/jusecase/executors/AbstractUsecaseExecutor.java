package org.jusecase.executors;

import org.jusecase.Usecase;
import org.jusecase.UsecaseExecutor;
import org.jusecase.UsecaseExecutorException;
import org.jusecase.VoidUsecase;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractUsecaseExecutor implements UsecaseExecutor {
    private final Map<Class<?>, Object> usecases = new HashMap<Class<?>, Object>();
    private final UsecaseRequestResolver requestResolver = new UsecaseRequestResolver();

    @SuppressWarnings("unchecked")
    public <Request, Response> Response execute(Request request) {
        if (request == null) {
            throw new UsecaseExecutorException("Request must not be null.");
        }

        Usecase<Request, Response> usecase = (Usecase<Request, Response>)getUsecase(request.getClass());
        return usecase.execute(request);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <Request> void executeVoid(Request request) {
        if (request == null) {
            throw new UsecaseExecutorException("Request must not be null.");
        }

        VoidUsecase<Request> usecase = (VoidUsecase<Request>)getUsecase(request.getClass());
        usecase.execute(request);
    }

    public Object getUsecase(Class<?> requestClass) {
        Object usecase = usecases.get(requestClass);
        if (usecase == null) {
            throw new UsecaseExecutorException("No usecase was found to handle request '" + requestClass.getName() + "'.");
        }
        return resolveUsecase(usecase);
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
