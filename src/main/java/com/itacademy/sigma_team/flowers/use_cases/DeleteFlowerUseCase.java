package com.itacademy.sigma_team.flowers.use_cases;

import com.itacademy.sigma_team.domain.Flower;

import java.util.function.Consumer;

public final class DeleteFlowerUseCase {

    private final Consumer<Flower> deleteFlowerMixin;

    public DeleteFlowerUseCase(Consumer<Flower> deleteFlowerMixin) {
        this.deleteFlowerMixin = deleteFlowerMixin;
    }

    public void exec(Flower flower) {
        this.deleteFlowerMixin.accept(flower);
    }

}
