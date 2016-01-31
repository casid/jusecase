package org.jusecase.manual;

import org.junit.Before;
import org.junit.Test;
import org.jusecase.example.trivial.AppendCharacters;
import org.jusecase.example.trivial.CalculateSum;

import static junit.framework.Assert.assertEquals;

public class ManualUsecaseExecutorTest {
    private ManualUsecaseExecutor executor;

    @Before
    public void setUp() throws Exception {
        executor = new ManualUsecaseExecutor();
    }

    @Test
    public void addOneUsecase() {
        executor.addUsecase(new AppendCharacters());
        thenAppendCharactersUsecaseCanBeExecuted();
    }

    @Test
    public void addMultipleUsecases() {
        executor.addUsecase(new CalculateSum());
        executor.addUsecase(new AppendCharacters());

        thenAppendCharactersUsecaseCanBeExecuted();
        thenCalculateSumUsecaseCanBeExecuted();
    }

    private void thenAppendCharactersUsecaseCanBeExecuted() {
        AppendCharacters.Request request = new AppendCharacters.Request('A', 5);
        assertEquals("AAAAA", executor.execute(request));
    }

    private void thenCalculateSumUsecaseCanBeExecuted() {
        CalculateSum.Request request = new CalculateSum.Request(2, 3);
        assertEquals(5, executor.execute(request));
    }
}