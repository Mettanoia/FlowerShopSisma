package com.itacademy.sigma_team.repositories_factories;

import com.itacademy.sigma_team.trees.repositories.TreeSqlRepository;
import com.itacademy.sigma_team.trees.use_cases.TreeGateway;

public final class TreeRepositoryFactory {

    public static TreeGateway getInstance() {return new TreeSqlRepository();}

}
