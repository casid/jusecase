package org.jusecase;

public interface Usecase<Request, Response> {
     Response execute(Request request);
}
