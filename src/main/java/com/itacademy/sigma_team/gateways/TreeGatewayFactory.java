package com.itacademy.sigma_team.gateways;


import com.itacademy.sigma_team.trees.repositories.TreeSqlRepository;

import java.sql.SQLException;

public final class TreeGatewayFactory implements com.itacademy.sigma_team.gateways.TreeGateway {

    @Override
    public TreeGateway getRepository() {

        return new TreeSqlRepository();

    }


}





