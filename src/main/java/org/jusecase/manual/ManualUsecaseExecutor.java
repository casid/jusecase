package org.jusecase.manual;

import net.jodah.typetools.TypeResolver;
import org.jusecase.Usecase;
import org.jusecase.UsecaseExecutor;
import org.omg.CORBA.Request;

import java.util.HashMap;
import java.util.Map;

public class ManualUsecaseExecutor implements UsecaseExecutor {

    private Map<Class<?>, Usecase<?, ?>> usecases = new HashMap<Class<?>, Usecase<?, ?>>();

    public <Request, Response> Response execute(Request request) {
        Usecase<Request, Response> concreteUsecase = getUsecase(request);
        return concreteUsecase.execute(request);
    }

    @SuppressWarnings("unchecked")
    private <Request, Response> Usecase<Request, Response> getUsecase(Request request) {
        return (Usecase<Request, Response>)usecases.get(request.getClass());
    }

    public void addUsecase(Usecase<?, ?> usecase) {
        Class<?>[] typeArgs = TypeResolver.resolveRawArguments(Usecase.class, usecase.getClass());
        usecases.put(typeArgs[0], usecase);
    }
}
