package com.itacademy.sigma_team.gateways.readers;

import java.util.Collection;

public interface Reader<T> {

    T get(String id);
    Collection <T> getAll();

}
