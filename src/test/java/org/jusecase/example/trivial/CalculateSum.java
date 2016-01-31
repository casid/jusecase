package org.jusecase.example.trivial;

import org.jusecase.Usecase;

public class CalculateSum implements Usecase<CalculateSum.Request, Integer> {
    public static class Request {
        public int a;
        public int b;

        public Request(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    public Integer execute(Request request) {
        return request.a + request.b;
    }
}
