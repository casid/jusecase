package org.jusecase;

import net.jodah.typetools.TypeResolver;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class UsecaseTest<Request, Response> {
    protected Usecase<Request, Response> usecase;
    protected Request request;
    protected Response response;
    protected Throwable error;

    @Before
    public void createRequest() {
        try {
            Class<?> requestClass = TypeResolver.resolveRawArguments(UsecaseTest.class, getClass())[0];
            request = (Request) requestClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate request. You need to override createRequest() and do it manually.", e);
        }
    }

    protected void whenRequestIsExecuted() {
        try {
            response = usecase.execute(request);
        } catch (Throwable e) {
            error = e;
        }
    }

    protected void thenResponseIs(Response expected) {
        assertEquals(expected, response);
    }

    protected void thenResponseIsNotNull() {
        assertNotNull(response);
    }

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
