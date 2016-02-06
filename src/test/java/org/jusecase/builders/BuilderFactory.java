package org.jusecase.builders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static <T> CollectionBuilder<T, List<T>> listWith(T ... items) {
        return new CollectionBuilder<T, List<T>>(new ArrayList<T>()).with(items);
    }

    public static <T> CollectionBuilder<T, Set<T>> setWith(T ... items) {
        return new CollectionBuilder<T, Set<T>>(new HashSet<T>()).with(items);
    }

    public static <T> ArrayBuilder<T> arrayWith(T ... items) {
        return new ArrayBuilder<T>(items);
    }
}
