package org.jusecase;

public abstract class VoidUsecaseTest<Request> extends UsecaseTestInterface<Request> {
    protected VoidUsecase<Request> usecase;

    protected void whenRequestIsExecuted() {
        try {
            usecase.execute(request);
        } catch (Throwable e) {
            error = e;
        }
    }
}
