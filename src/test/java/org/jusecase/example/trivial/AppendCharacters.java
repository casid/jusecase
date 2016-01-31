package org.jusecase.example.trivial;

import org.jusecase.Usecase;

public class AppendCharacters implements Usecase<AppendCharacters.Request, String> {
    public static class Request {
        public char character;
        public int count;

        public Request(char character, int count) {
            this.character = character;
            this.count = count;
        }
    }

    public String execute(Request request) {
        String result = "";
        for (int i = 0; i < request.count; ++i) {
            result += request.character;
        }
        return result;
    }
}
