package com.itacademy.sigma_team.gateways.readers;

import java.io.IOException;

@FunctionalInterface
public interface Deleter<T> {
    void delete(T dto) throws IOException;
}
