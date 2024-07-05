package com.itacademy.sigma_team.flowers.use_cases;

import com.itacademy.sigma_team.domain.Flower;

import java.util.function.Consumer;

public final class AddFlowerUseCase {

    private final Consumer<Flower> addFlowerMixin;

    public AddFlowerUseCase(Consumer<Flower> addFlowerMixin) {
        this.addFlowerMixin = addFlowerMixin;
    }

    public void exec(Flower flower) {
        this.addFlowerMixin.accept(flower);
    }

}
