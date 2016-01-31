package org.jusecase.manual;

import net.jodah.typetools.TypeResolver;
import org.jusecase.AbstractUsecaseExecutor;
import org.jusecase.Usecase;

public class ManualUsecaseExecutor extends AbstractUsecaseExecutor {

    public <Request, Response> Response execute(Request request) {
        Usecase<Request, Response> usecase = getUsecase(request);
        return usecase.execute(request);
    }

    public void addUsecase(Usecase<?, ?> usecase) {
        addUsecase(getRequestClass(usecase.getClass()), usecase);
    }

    public <T extends Usecase> void addUsecase(Factory<T> factory) {
        addUsecase(getRequestClassFromFactory(factory.getClass()), factory);
    }

    @Override
    protected Object resolveUsecase(Object usecase) {
        if (usecase instanceof Usecase) {
            return usecase;
        }

        if (usecase instanceof Factory) {
            return ((Factory)usecase).create();
        }

        return null;
    }

    private Class<?> getRequestClassFromFactory(Class<?> factoryClass) {
        Class<?>[] factoryTypes = TypeResolver.resolveRawArguments(Factory.class, factoryClass);
        return getRequestClass(factoryTypes[0]);
    }
}
