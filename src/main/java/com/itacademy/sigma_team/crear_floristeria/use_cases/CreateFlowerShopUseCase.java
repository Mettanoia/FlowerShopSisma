package com.itacademy.sigma_team.crear_floristeria.use_cases;

import com.itacademy.sigma_team.flower_shop.FlowerShop;

public final class CreateFlowerShopUseCase {

    private final FlowerShopGateway flowerShopGateway;

    public CreateFlowerShopUseCase(FlowerShopGateway flowerShopGateway) {
        this.flowerShopGateway = flowerShopGateway;
    }

    public void exec(FlowerShop flowerShop) {
        this.flowerShopGateway.create(flowerShop);
    }

}
