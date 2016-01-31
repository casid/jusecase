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

## Built in support for dependency injection
Dependency injection details are completely hidden to your application.

For instance, you might have a central class that encapsulates your business logic. In terms of JUsecase, this would be a UsecaseExecutor:

```java
public interface UsecaseExecutor {
    <Request, Response> Response execute(Request request);
}
```

This means the main module (UI, REST, TimerBean, ...) simply fires a request at the RequestExecutor. Which class is exactly executing the request doesn't concern the main module. For example a REST call:

```java
@POST("login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Login.Response login(Login.Request request) {
    return Logic.instance.execute(request);
}
```

And to be sure that the request can actually be handled, we add this unit test:

```java
public class LogicTest extends UsecaseExecutorTest {

    @Test
    public void requestsCanBeExecuted() {
        givenExecutor(Logic.instance);
        
        thenUsecaseCanBeExecuted(Login.class);
        // ... in a real world application more usecases will follow here.
    }
}
```

### Manual dependency injection

This test will become green by implementing a simple Logic class. Note that it derives from ManualUsecaseExecutor. This means all dependency injection is done by hand.

```java
public class Logic extends ManualUsecaseExecutor {
    public static Logic instance = new Logic();

    public Logic() {
        addUsecase(new Login());
    }
}
```

Imagine an application with a lot of usecases. Yet easy to implement, a problem of the above solution is that all usecases are generated instantly. In a real world application every usecase would require lower level dependencies like entity gateways or external plugins. In case you want to achieve that a concrete usecase is only created, when it is actually used, the manual executor supports factories for usecases:

```java
public class Logic extends ManualUsecaseExecutor {
    public static Logic instance = new Logic();

    public Logic() {
        addUsecase(new Factory<Login>() {
            public Login create() {
                return new Login();
            }
        });
    }
}
```

Notice that absolutely nothing needs to be changed in your test when switching to a different way of instantiating usecases.

### Automatic dependency injection
There currently is no golden way to use dependency injection. Both manual and automatic approaches have benefits and drawbacks. This is why JUsecase doesn't require you to make that decision at the beginning of your project.

```java
public class Logic extends GuiceUsecaseExecutor {
    public static Logic instance = new Logic();

    public Logic() {
        addUsecase(Login.class);
    }
}
```

TODO add link to jusecase-guice repository.

If you are unsure what method to use, start with manual injection. Switching to a dependency injection framework is a matter of minutes. And remember, no tests need to be changed and your tests will warn you instantly if the dependency injection framework fails to initialize a usecase!
