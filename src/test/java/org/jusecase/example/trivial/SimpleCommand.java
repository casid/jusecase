package org.jusecase.example.trivial;

import org.jusecase.VoidUsecase;

public class SimpleCommand implements VoidUsecase<SimpleCommand.Request> {

    public static String messages = "";

    public SimpleCommand() {
        messages = "";
    }

    @Override
    public void execute(Request request) {
        messages += request.message;
    }

    public static class Request {
        public final String message;

        public Request(String message) {
            this.message = message;
        }
    }
}
