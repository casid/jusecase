package org.jusecase.manual;

import org.junit.Before;
import org.junit.Test;
import org.jusecase.Usecase;
import org.jusecase.UsecaseExecutorException;
import org.jusecase.example.trivial.AppendCharacters;
import org.jusecase.example.trivial.CalculateSum;

import static junit.framework.Assert.assertEquals;

public class ManualUsecaseExecutorTest {
    private ManualUsecaseExecutor executor;
    private UsecaseExecutorException exception;

    @Before
    public void setUp() throws Exception {
        executor = new ManualUsecaseExecutor();
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

        assertEquals("Could not resolve usecase request type for class 'org.jusecase.Usecase'", exception.getMessage());
    }

    private void thenAppendCharactersCanBeExecuted() {
        AppendCharacters.Request request = new AppendCharacters.Request('A', 5);
        assertEquals("AAAAA", executor.execute(request));
    }

    private void thenCalculateSumCanBeExecuted() {
        CalculateSum.Request request = new CalculateSum.Request(2, 3);
        assertEquals(5, executor.execute(request));
    }
}