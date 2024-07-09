package com.itacademy.sigma_team.gateways.readers;

import java.io.IOException;

@FunctionalInterface
public interface Adder<T> {
    T add(T dto) throws IOException;
}
