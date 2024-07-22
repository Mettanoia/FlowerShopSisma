package com.itacademy.sigma_team.common;

import java.util.Optional;

public interface Adder<T> {
    Optional<T> add(T t);
}