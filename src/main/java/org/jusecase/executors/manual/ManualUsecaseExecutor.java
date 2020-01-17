package org.jusecase.executors.manual;

import org.jusecase.Usecase;
import org.jusecase.VoidUsecase;
import org.jusecase.executors.AbstractUsecaseExecutor;
import org.jusecase.util.GenericTypeResolver;

public class ManualUsecaseExecutor extends AbstractUsecaseExecutor {

    public void addUsecase(Usecase<?, ?> usecase) {
        addUsecase(getRequestClass(usecase.getClass()), usecase);
    }

    public void addUsecase(VoidUsecase<?> usecase) {
        addUsecase(getRequestClass(usecase.getClass()), usecase);
    }

    public <T> void addUsecase(Factory<T> factory) {
        addUsecase(getRequestClassFromFactory(factory.getClass()), factory);
    }

    @Override
    protected Object resolveUsecase(Object usecase) {
        if (usecase instanceof Factory) {
            return ((Factory<?>) usecase).create();
        }

        return usecase;
    }

    private Class<?> getRequestClassFromFactory(Class<?> factoryClass) {
        Class<?> factoryType = GenericTypeResolver.resolve(Factory.class, factoryClass, 0);
        return getRequestClass(factoryType);
    }
}
