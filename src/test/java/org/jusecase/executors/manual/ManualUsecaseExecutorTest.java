package org.jusecase.executors.manual;

import org.junit.Before;
import org.junit.Test;
import org.jusecase.Usecase;
import org.jusecase.UsecaseExecutorException;
import org.jusecase.example.trivial.AppendCharacters;
import org.jusecase.example.trivial.CalculateSum;

import static org.junit.Assert.*;

public class ManualUsecaseExecutorTest {
    private ManualUsecaseExecutor executor;
    private UsecaseExecutorException exception;

    @Before
    public void setUp() throws Exception {
        executor = new ManualUsecaseExecutor();
    }

    @Test
    public void nullRequest() {
        try {
            executor.execute(null);
        } catch (UsecaseExecutorException e) {
            exception = e;
        }

        thenExceptionMessageIs("Request must not be null.");
    }

    @Test
    public void noUsecaseFound() {
        try {
            executor.execute(new AppendCharacters.Request('A', 1));
        } catch (UsecaseExecutorException e) {
            exception = e;
        }

        thenExceptionMessageIs("No usecase was found to handle request 'org.jusecase.example.trivial.AppendCharacters$Request'.");
    }

    @Test
    public void addOneUsecase() {
        executor.addUsecase(new AppendCharacters());
        thenAppendCharactersCanBeExecuted();
    }

    @Test
    public void addMultipleUsecases() {
        executor.addUsecase(new CalculateSum());
        executor.addUsecase(new AppendCharacters());

        thenAppendCharactersCanBeExecuted();
        thenCalculateSumCanBeExecuted();
    }

    @Test
    public void addUsecaseWithAlreadyExistingRequest() {
        executor.addUsecase(new CalculateSum());

        try {
            executor.addUsecase(new CalculateSum());
        } catch (UsecaseExecutorException e) {
            exception = e;
        }

        thenExceptionMessageIs("Request 'org.jusecase.example.trivial.CalculateSum$Request' is already handled by a usecase.");
    }

    @Test
    public void addOneUsecaseFactory() {
        executor.addUsecase(new Factory<AppendCharacters>() {
            public AppendCharacters create() {
                return new AppendCharacters();
            }
        });
        thenAppendCharactersCanBeExecuted();
    }

    @Test
    public void addOneUsecaseFactoryWithoutRequestInformation() {
        try {
            executor.addUsecase(new Factory<Usecase>() {
                public Usecase create() {
                    return new AppendCharacters();
                }
            });
        } catch (UsecaseExecutorException e) {
            exception = e;
        }

        thenExceptionMessageIs("Could not resolve usecase request type for class 'org.jusecase.Usecase'");
    }

    private void thenAppendCharactersCanBeExecuted() {
        AppendCharacters.Request request = new AppendCharacters.Request('A', 5);
        assertEquals("AAAAA", executor.execute(request));
    }

    private void thenCalculateSumCanBeExecuted() {
        CalculateSum.Request request = new CalculateSum.Request(2, 3);
        assertEquals(5, executor.execute(request));
    }

    private void thenExceptionMessageIs(String expected) {
        assertNotNull("Expected exception with message '" + expected + "', but nothing was thrown.", exception);
        assertEquals(expected, exception.getMessage());
    }
}