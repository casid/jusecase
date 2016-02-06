package org.jusecase.builders;

import java.util.Arrays;
import java.util.Collection;

public class CollectionBuilder<T, CollectionType extends Collection<T>> implements Builder<CollectionType> {
    private final CollectionType collection;

    public CollectionBuilder(CollectionType collection) {
        this.collection = collection;
    }

    public CollectionBuilder<T, CollectionType> with(T ... items) {
        collection.addAll(Arrays.asList(items));
        return this;
    }

    public CollectionType build() {
        return collection;
    }
}
