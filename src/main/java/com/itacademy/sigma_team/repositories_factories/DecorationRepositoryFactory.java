package com.itacademy.sigma_team.repositories_factories;

import com.itacademy.sigma_team.decorations.repositories.DecorationSqlRepository;
import com.itacademy.sigma_team.decorations.repositories.ShopSpecificDecorationRepository;
import com.itacademy.sigma_team.decorations.use_cases.DecorationGateway;

public final class DecorationRepositoryFactory {

    public static DecorationGateway getInstance() {return new ShopSpecificDecorationRepository();}

}
