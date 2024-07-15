package com.itacademy.sigma_team.gateway_mixins.writers;

import java.io.IOException;

@FunctionalInterface
public interface Adder<T> {
    void add(T dto) throws IOException;
}
