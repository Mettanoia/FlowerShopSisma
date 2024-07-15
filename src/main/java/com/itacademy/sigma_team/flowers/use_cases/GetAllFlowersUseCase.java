package com.itacademy.sigma_team.flowers.use_cases;

import com.itacademy.sigma_team.domain.Flower;

import java.util.Collection;
import java.util.function.Supplier;

public final class GetAllFlowersUseCase {

    private final Supplier<Collection<Flower>> getAllFlowersMixin;

    public GetAllFlowersUseCase(Supplier<Collection<Flower>> getAllFlowersMixin) {
        this.getAllFlowersMixin = getAllFlowersMixin;
    }

    public Collection<Flower> exec() {
        return this.getAllFlowersMixin.get();
    }

}
