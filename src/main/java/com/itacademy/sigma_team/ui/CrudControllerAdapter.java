package com.itacademy.sigma_team.ui;

import com.itacademy.sigma_team.common.Adder;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class CrudControllerAdapter<T, ID> implements Consumer<T>, Adder<T>, Function<ID, Optional<T>>, Supplier<Collection<T>> {

    private final Consumer<T> deleteMixin;
    private final Adder<T> addMixin;
    private final Function<ID, Optional<T>> getByIdMixin;
    private final Supplier<Collection<T>> getAllMixin;


    private CrudControllerAdapter(Builder<T,ID> builder) {
        this.deleteMixin = builder.deleteMixin;
        this.addMixin = builder.addMixin;
        this.getByIdMixin = builder.getByIdMixin;
        this.getAllMixin = builder.getAllMixin;
    }

    @Override
    public void accept(T t) {
        deleteMixin.accept(t);
    }

    @Override
    public Optional<T> add(T t) {
        return addMixin.add(t);
    }

    @Override
    public Optional<T> apply(ID id) {
        return getByIdMixin.apply(id);
    }

    @Override
    public Collection<T> get() {
        return getAllMixin.get();
    }

    public static <T, ID> Builder<T, ID> builder() {
        return new Builder<>();
    }


    public static class Builder<T, ID> {

        private Consumer<T> deleteMixin = entity -> {
            throw new UnsupportedOperationException("Delete operation is not supported.");
        };

        private Adder<T> addMixin = entity -> {
            throw new UnsupportedOperationException("Add operation is not supported.");
        };

        private Function<ID, Optional<T>> getByIdMixin = id -> {
            throw new UnsupportedOperationException("Get by ID operation is not supported.");
        };

        private Supplier<Collection<T>> getAllMixin = () -> {
            throw new UnsupportedOperationException("Get all operation is not supported.");
        };


        public Builder<T, ID> deleteMixin(Consumer<T> deleteMixin) {
            this.deleteMixin = deleteMixin;
            return this;
        }

        public Builder<T, ID> addMixin(Adder<T> addMixin) {
            this.addMixin = addMixin;
            return this;
        }

        public Builder<T, ID> getByIdMixin(Function<ID, Optional<T>> getByIdMixin) {
            this.getByIdMixin = getByIdMixin;
            return this;
        }

        public Builder<T, ID> getAllMixin(Supplier<Collection<T>> getAllMixin) {
            this.getAllMixin = getAllMixin;
            return this;
        }


        public CrudControllerAdapter<T, ID> build() {
            return new CrudControllerAdapter<>(this);
        }

    }
}
