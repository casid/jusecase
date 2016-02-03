package org.jusecase.builders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListBuilder<T> implements Builder<List<T>> {
    private final List<T> list = new ArrayList<T>();

    public ListBuilder<T> with(T ... items) {
        list.addAll(Arrays.asList(items));
        return this;
    }

    public List<T> build() {
        return list;
    }
}
