package com.itacademy.sigma_team.gateway_mixins.readers;

import java.io.IOException;
import java.util.Collection;

public interface Reader<T> {

    T get(String id) throws IOException;
    Collection<T> getAll() throws IOException;

}
