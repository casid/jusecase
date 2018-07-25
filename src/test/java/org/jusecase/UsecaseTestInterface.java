package org.jusecase;

import net.jodah.typetools.TypeResolver;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class UsecaseTestInterface<Request> {
    protected Request request;
    protected Throwable error;

    @SuppressWarnings("unchecked")
    @Before
    public void createRequest() {
        try {
            Class<?> requestClass = TypeResolver.resolveRawArguments(UsecaseTestInterface.class, getClass())[0];
            request = (Request) requestClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate request. You need to override createRequest() and do it manually.", e);
        }
    }

    protected abstract void whenRequestIsExecuted();

    protected void thenErrorIs(Throwable expected) {
        assertEquals(expected, error);
    }

    protected void thenErrorIs(Class<? extends Throwable> expected) {
        assertEquals(expected, error.getClass());
    }

    protected void thenErrorMessageIs(String expected) {
        assertNotNull("Expected error with message '" + expected + "', but nothing was thrown.", error);
        assertEquals(expected, error.getMessage());
    }
}
