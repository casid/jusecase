package org.jusecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class UsecaseTest<Request, Response> extends UsecaseTestInterface<Request> {
    protected Usecase<Request, Response> usecase;
    protected Response response;

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
}
