package org.jusecase.builders;

public class BuilderFactory {
    public static <T> T a(Builder<T> builder) {
        return builder.build();
    }

    public static InputStreamBuilder inputStream() {
        return new InputStreamBuilder();
    }

    public static DateBuilder date() {
        return new DateBuilder();
    }

    public static <T> ListBuilder<T> listWith(T ... items) {
        return new ListBuilder<T>().with(items);
    }

    public static <T> ArrayBuilder<T> arrayWith(T ... items) {
        return new ArrayBuilder<T>(items);
    }
}
