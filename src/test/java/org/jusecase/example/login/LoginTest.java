package org.jusecase.example.login;

import org.junit.Before;
import org.junit.Test;
import org.jusecase.builder.Builder;
import org.jusecase.UsecaseTest;
import org.jusecase.example.login.Login.Request;
import org.jusecase.example.login.Login.Response;

import static org.junit.Assert.assertEquals;
import static org.jusecase.builder.BuilderFactory.a;

public class LoginTest extends UsecaseTest<Request, Response> {
    @Before
    public void setUp() {
        usecase = new Login();
    }

    @Test
    public void nullRequest() {
        givenRequest(null);
        whenRequestIsExecuted();
        thenErrorMessageIs("Request must not be null");
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
        thenErrorMessageIs("You are not authorized to enter");
    }

    @Test
    public void backDoor() {
        givenRequest(a(request().withPassword("Chuck Norris")));
        whenRequestIsExecuted();
        thenWelcomeMessageIs("Your password is too strong to prevent you from entering");
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
                    .withName("andy@mazebert.com")
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
