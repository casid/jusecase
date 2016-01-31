package org.jusecase.example.login.plugins;

public interface AuthPlugin {
    boolean isAuthorized(String name, String password);
}
