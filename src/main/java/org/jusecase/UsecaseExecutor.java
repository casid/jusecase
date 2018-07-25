package org.jusecase;

public interface UsecaseExecutor {
    <Request, Response> Response execute(Request request);

    <Request> void executeVoid(Request request);
}
