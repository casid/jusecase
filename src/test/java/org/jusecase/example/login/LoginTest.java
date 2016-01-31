package org.jusecase.example.login;

import org.junit.Before;
import org.junit.Test;
import org.jusecase.builder.Builder;
import org.jusecase.UsecaseTest;
import org.jusecase.example.login.Login.Request;
import org.jusecase.example.login.Login.Response;
import org.jusecase.example.login.plugins.AuthPluginCoach;

import static org.junit.Assert.assertEquals;
import static org.jusecase.builder.BuilderFactory.a;

public class LoginTest extends UsecaseTest<Request, Response> {
    private AuthPluginCoach authPlugin;

    @Before
    public void setUp() {
        authPlugin = new AuthPluginCoach();
        usecase = new Login(authPlugin);
    }

    @Test
    public void nullLoginName() {
        givenRequest(a(request().withName(null)));
        whenRequestIsExecuted();
        thenErrorMessageIs("Login name must not be null");
    }

    @Test
    public void nullLoginPassword() {
        givenRequest(a(request().withPassword(null)));
        whenRequestIsExecuted();
        thenErrorMessageIs("Password must not be null");
    }

    @Test
    public void notAuthorized() {
        givenRequest(a(request()));
        whenRequestIsExecuted();
        thenUserIsNotAuthorized();
    }

    @Test
    public void wrongName() {
        authPlugin.givenUserIsAuthorized("admin@company.com", "admin");
        givenRequest(a(request()
                .withName("user@company.com")
                .withPassword("admin")));

        whenRequestIsExecuted();
        thenUserIsNotAuthorized();
    }

    @Test
    public void wrongPassword() {
        authPlugin.givenUserIsAuthorized("admin@company.com", "admin");
        givenRequest(a(request()
                .withName("admin@company.com")
                .withPassword("?")));

        whenRequestIsExecuted();
        thenUserIsNotAuthorized();
    }

    @Test
    public void authorized() {
        authPlugin.givenUserIsAuthorized("admin@company.com", "admin");
        givenRequest(a(request()
            .withName("admin@company.com")
            .withPassword("admin")));

        whenRequestIsExecuted();
        thenWelcomeMessageIs("Welcome, admin@company.com");
    }

    @Test
    public void backDoor() {
        givenRequest(a(request().withPassword("Chuck Norris")));
        whenRequestIsExecuted();
        thenWelcomeMessageIs("Your password is too strong to prevent you from entering");
    }

    private void thenUserIsNotAuthorized() {
        thenErrorMessageIs("You are not authorized to enter");
    }

    private void thenWelcomeMessageIs(String expected) {
        assertEquals(expected, response.welcomeMessage);
    }

    private RequestBuilder request() {
        return new RequestBuilder().golden();
    }

    private static class RequestBuilder implements Builder<Request> {
        private Request request = new Request();

        public RequestBuilder golden() {
            return this
                    .withName("user@company.com")
                    .withPassword("TopSecret");
        }

        public RequestBuilder withName(String value) {
            request.name = value;
            return this;
        }

        public RequestBuilder withPassword(String value) {
            request.password = value;
            return this;
        }

        public Request build() {
            return request;
        }
    }
}
