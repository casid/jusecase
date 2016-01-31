package org.jusecase.example;

import org.jusecase.example.login.Login;
import org.jusecase.example.login.plugins.ActiveDirectoryAuthPlugin;
import org.jusecase.example.login.plugins.AuthPlugin;
import org.jusecase.example.trivial.AppendCharacters;
import org.jusecase.example.trivial.CalculateSum;
import org.jusecase.executors.manual.Factory;
import org.jusecase.executors.manual.ManualUsecaseExecutor;

public class Logic extends ManualUsecaseExecutor {
    public static Logic instance = new Logic();

    public Logic() {
        addUsecase(new AppendCharacters());
        addUsecase(new CalculateSum());

        // We use a factory for the login.
        // Let's pretend the AuthPlugin is expensive to initialize.
        addUsecase(new Factory<Login>() {
            public Login create() {
                return new Login(authPlugin());
            }
        });
    }

    private AuthPlugin authPlugin() {
        return new ActiveDirectoryAuthPlugin();
    }
}
