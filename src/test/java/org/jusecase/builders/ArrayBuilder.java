package org.jusecase.builders;

public class ArrayBuilder<T> implements Builder<T[]> {
    private final T[] array;

    public ArrayBuilder(T[] array) {
        this.array = array;
    }

    public T[] build() {
        return array;
    }
}
