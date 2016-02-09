package org.jusecase.example.login.plugins;

import java.util.HashSet;
import java.util.Set;

public class AuthPluginMock implements AuthPlugin {
    private Set<String> authorizedUsers = new HashSet<String>();

    public boolean isAuthorized(String name, String password) {
        return authorizedUsers.contains(name + password);
    }

    public void givenUserIsAuthorized(String name, String password) {
        authorizedUsers.add(name + password);
    }
}
