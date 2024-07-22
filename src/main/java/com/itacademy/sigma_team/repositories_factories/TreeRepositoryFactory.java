package com.itacademy.sigma_team.repositories_factories;

import com.itacademy.sigma_team.trees.repositories.ShopSpecificTreeRepository;
import com.itacademy.sigma_team.trees.use_cases.TreeGateway;

public final class TreeRepositoryFactory {

    public static TreeGateway getInstance() {return new ShopSpecificTreeRepository();}

}
