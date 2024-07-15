package com.itacademy.sigma_team.gateway_mixins.writers;

import java.io.IOException;

@FunctionalInterface
public interface Deleter<T> {
    void delete(T dto) throws IOException;
}
