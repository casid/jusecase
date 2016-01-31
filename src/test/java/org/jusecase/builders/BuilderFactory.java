package org.jusecase.builders;

public class BuilderFactory {
    public static <T> T a(Builder<T> builder) {
        return builder.build();
    }
}
