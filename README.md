# JUsecase
[![Build Status](https://travis-ci.org/casid/jusecase.svg?branch=master)](https://travis-ci.org/casid/jusecase)
[![Coverage Status](https://coveralls.io/repos/github/casid/jusecase/badge.svg?branch=master)](https://coveralls.io/github/casid/jusecase?branch=master)

Java framework for a usecase centric architecture as proposed by Uncle Bob and others. Supports Java 1.6+.

## Setup
JUsecase is available on maven central repository:
```xml
<dependency>
    <groupId>org.jusecase</groupId>
    <artifactId>jusecase</artifactId>
    <version>0.1.0</version>
</dependency>
<dependency>
    <groupId>org.jusecase</groupId>
    <artifactId>jusecase</artifactId>
    <version>0.1.0</version>
    <type>test-jar</type>
    <scope>test</scope>
</dependency>
```

Don't forget to add the test-jar to your project. It provides those parts that make JUsecase truely awesome for TDD.

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

With JUsecase, writing the first failing test is very straight-forward.

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

In a real world usecase you would throw a custom exception that better suits the project you are actually working with. Error handling is completely up to you and your requirements.

I recommend to have a look at the entire login example:
- [Login.java](https://github.com/casid/jusecase/blob/master/src/test/java/org/jusecase/example/login/Login.java)
- [LoginTest.java](https://github.com/casid/jusecase/blob/master/src/test/java/org/jusecase/example/login/LoginTest.java)

## Built in support for dependency injection
Dependency injection details are completely hidden from your application and managed at one central place.

For instance, you might want to have a central class that encapsulates your business logic. In terms of JUsecase, this would be a UsecaseExecutor:

```java
public interface UsecaseExecutor {
    <Request, Response> Response execute(Request request);
}
```

This means the main module (UI, REST, TimerBean, ...) fires a request at the UsecaseExecutor. Which class is exactly executing the request doesn't concern the main module. For example a REST call could come down to this:

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

This test will become green by implementing a simple Logic class.

```java
public class Logic extends ManualUsecaseExecutor {
    public static Logic instance = new Logic();

    public Logic() {
        addUsecase(new Login());
    }
}
```

### Manual dependency injection
Note that the above class derives from ManualUsecaseExecutor. This means all dependency injection is done by hand.

Imagine an application with a lot of usecases. Yet easy to implement, a problem of the above solution is that all usecases are generated instantly. In a real world application every usecase would require lower level dependencies like entity gateways or external plugins. In case you want to achieve that a concrete usecase is only created, when it is actually used, the manual executor supports to add usecase factories:

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

Notice that absolutely nothing needs to be changed in your unit test when switching to usecase factories.

### Automatic dependency injection
It is also possible to use a dependency injection framework with JUsecase. Have a look at the Logic class derived from GuiceUsecaseExecutor.

```java
public class Logic extends GuiceUsecaseExecutor {
    public static Logic instance = new Logic();

    public Logic() {
        super(Guice.createInjector());
        addUsecase(Login.class);
    }
}
```

Note: JUsecase does not come with Guice support by default. To add guice support, follow the instructions on [jusecase-guice](https://github.com/casid/jusecase-guice). This should also be a good starting point if you would like to add support for other dependency injection frameworks.

Note: There currently is no golden way to use dependency injection. Both manual and automatic approaches have benefits and drawbacks. This is why JUsecase doesn't require you to make that decision at the beginning of your project.

If you are unsure what dependency injection method to use, start with manual injection. The good news is: Switching to a dependency injection framework will still be very straight forward. Is is because all dependencies are managed at one central place. And remember, no tests need to be changed when you change the dependency injection method. Your unit tests will check that your usecases can be generated, no matter what.
