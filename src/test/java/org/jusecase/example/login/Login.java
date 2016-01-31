package org.jusecase.example.login;

import org.jusecase.Usecase;

public class Login implements Usecase<Login.Request, Login.Response> {
    public static class Request {
        public String name;
        public String password;
    }

    public static class Response {
        public String welcomeMessage;
    }

    public Response execute(Request request) {
        validateRequest(request);

        if ("Chuck Norris".equals(request.password)) {
            Response response = new Response();
            response.welcomeMessage = "Your password is too strong to prevent you from entering";
            return response;
        }

        throw new RuntimeException("You are not authorized to enter");
    }

    private void validateRequest(Request request) {
        if (request.name == null) {
            throw new RuntimeException("Login name must not be null");
        }

        if (request.password == null) {
            throw new RuntimeException("Password must not be null");
        }
    }
}
