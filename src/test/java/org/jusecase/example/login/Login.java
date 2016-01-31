package org.jusecase.example.login;

import org.jusecase.Usecase;
import org.jusecase.example.login.plugins.AuthPlugin;

public class Login implements Usecase<Login.Request, Login.Response> {
    public static class Request {
        public String name;
        public String password;
    }

    public static class Response {
        public String welcomeMessage;
    }

    private final AuthPlugin authPlugin;

    public Login(AuthPlugin authPlugin) {
        this.authPlugin = authPlugin;
    }

    public Response execute(Request request) {
        validateRequest(request);
        return login(request);
    }

    private void validateRequest(Request request) {
        if (request.name == null) {
            throw new RuntimeException("Login name must not be null");
        }

        if (request.password == null) {
            throw new RuntimeException("Password must not be null");
        }
    }

    private Response login(Request request) {
        if (authPlugin.isAuthorized(request.name, request.password)) {
            return success(request);
        } else {
            return tryLoginBackdoor(request);
        }
    }

    private Response success(Request request) {
        Response response = new Response();
        response.welcomeMessage = "Welcome, " + request.name;
        return response;
    }

    private Response tryLoginBackdoor(Request request) {
        if ("Chuck Norris".equals(request.password)) {
            Response response = new Response();
            response.welcomeMessage = "Your password is too strong to prevent you from entering";
            return response;
        }

        throw new RuntimeException("You are not authorized to enter");
    }
}
