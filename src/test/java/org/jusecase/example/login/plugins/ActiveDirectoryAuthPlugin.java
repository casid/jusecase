package org.jusecase.example.login.plugins;

public class ActiveDirectoryAuthPlugin implements AuthPlugin {
    public boolean isAuthorized(String name, String password) {
        // Place for active directory specific code that the business logic does not want to know about.
        return false;
    }
}
