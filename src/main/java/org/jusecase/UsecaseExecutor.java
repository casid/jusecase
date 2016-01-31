package org.jusecase;

public interface UsecaseExecutor {
    <Request, Response> Response execute(Request request);
    <Request, Response> Usecase<Request, Response> getUsecase(Class<?> requestClass);
}
