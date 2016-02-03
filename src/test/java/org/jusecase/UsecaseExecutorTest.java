package org.jusecase;

import org.jusecase.executors.AbstractUsecaseExecutor;
import org.jusecase.executors.UsecaseRequestResolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class UsecaseExecutorTest {
    protected AbstractUsecaseExecutor executor;
    protected UsecaseRequestResolver requestResolver = new UsecaseRequestResolver();

    public void givenExecutor(AbstractUsecaseExecutor executor) {
        this.executor = executor;
    }

    public void thenUsecaseCanBeExecuted(Class<?> usecaseClass) {
        Class<?> requestClass = requestResolver.getRequestClass(usecaseClass);
        Usecase<?, ?> usecase = executor.getUsecase(requestClass);
        assertNotNull("No usecase was found for request '" + requestClass.getName() + "'", usecase);
        assertEquals(usecaseClass, usecase.getClass());
    }
}
