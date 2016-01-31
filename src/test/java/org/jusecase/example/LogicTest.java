package org.jusecase.example;

import org.junit.Test;
import org.jusecase.UsecaseExecutorTest;
import org.jusecase.example.login.Login;
import org.jusecase.example.trivial.AppendCharacters;
import org.jusecase.example.trivial.CalculateSum;

public class LogicTest extends UsecaseExecutorTest {

    @Test
    public void requestsCanBeExecuted() {
        givenExecutor(Logic.instance);

        thenUsecaseCanBeExecuted(Login.class);
        thenUsecaseCanBeExecuted(AppendCharacters.class);
        thenUsecaseCanBeExecuted(CalculateSum.class);
    }
}
