package org.jusecase;

public class UsecaseExecutorException extends RuntimeException {
    public UsecaseExecutorException(String message) {
        super(message);
    }

    public UsecaseExecutorException(String message, Throwable cause) {
        super(message, cause);
    }
}
