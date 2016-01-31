# JUsecase
[![Build Status](https://travis-ci.org/casid/jusecase.svg?branch=master)](https://travis-ci.org/casid/jusecase)
[![Coverage Status](https://coveralls.io/repos/github/casid/jusecase/badge.svg?branch=master)](https://coveralls.io/github/casid/jusecase?branch=master)

Java framework for a usecase centric architecture as proposed by Uncle Bob and others. Supports Java 1.6+.

## Easy to test
Imagine a very common usecase: A user logs in at your system.
```java
public class Login implements Usecase<Login.Request, Login.Response> {
    public static class Request {
        public String name;
        public String password;
    }

    public static class Response {
        public String welcomeMessage;
    }

    public Response execute(Request request) {
        return null;
    }
```

With JUsecase, writing the first failing test is a piece of cake.

```java
public class LoginTest extends UsecaseTest<Request, Response> {
    @Before
    public void setUp() {
        usecase = new Login();
    }
    
    @Test
    public void nullRequest() {
        givenRequest(null);
        whenRequestIsExecuted();
        thenErrorMessageIs("Login request must not be null");
    }
```

Notice that no boilerplate code needs to be written. The given, when, then methods are already there, provided by the abstract UsecaseTest class.

To make the test green, you would simply write:

```java
public Response execute(Request request) {
    throw new RuntimeException("Login request must not be null");
}
```

Of course you can throw a custom exception that better suits the project you are actually working with.
