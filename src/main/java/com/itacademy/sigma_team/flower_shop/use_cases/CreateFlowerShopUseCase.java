package com.itacademy.sigma_team.flower_shop.use_cases;

import com.itacademy.sigma_team.flower_shop.FlowerShop;

import java.util.function.Consumer;

public final class CreateFlowerShopUseCase {

    private final Consumer<FlowerShop> createFlowerShopMixin;

    public CreateFlowerShopUseCase(Consumer<FlowerShop> createFlowerShopMixin) {
        this.createFlowerShopMixin = createFlowerShopMixin;
    }

    public void exec(FlowerShop flowerShop) {
        this.createFlowerShopMixin.accept(flowerShop);
    }

}
