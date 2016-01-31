package org.jusecase.example;

import org.jusecase.example.login.Login;
import org.jusecase.example.trivial.AppendCharacters;
import org.jusecase.example.trivial.CalculateSum;
import org.jusecase.executors.manual.Factory;
import org.jusecase.executors.manual.ManualUsecaseExecutor;

public class Logic extends ManualUsecaseExecutor {
    public static Logic instance = new Logic();

    public Logic() {
        addUsecase(new Login());
        addUsecase(new AppendCharacters());

        // For this one, we pretend to need a factory.
        addUsecase(new Factory<CalculateSum>() {
            public CalculateSum create() {
                return new CalculateSum();
            }
        });
    }
}
