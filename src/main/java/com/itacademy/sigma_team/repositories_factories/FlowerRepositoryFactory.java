package com.itacademy.sigma_team.repositories_factories;

import com.itacademy.sigma_team.flowers.repositories.ShopSpecificFlowerRepository;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;

public final class FlowerRepositoryFactory {
    public static FlowerGateway getRepository() {return new ShopSpecificFlowerRepository();}
}
