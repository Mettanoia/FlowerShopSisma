package com.itacademy.sigma_team.repositories_factories;

import com.itacademy.sigma_team.flower_shop.repositories.FlowerShopSqlRepository;
import com.itacademy.sigma_team.flower_shop.use_cases.FlowerShopGateway;

public final class FlowerShopRepositoryFactory {

    public static FlowerShopGateway getInstance() {return new FlowerShopSqlRepository();}

}
