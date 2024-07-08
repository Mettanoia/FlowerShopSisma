package com.itacademy.sigma_team.flowers.repositories;

import com.itacademy.sigma_team.flowers.repositories.FlowerSqlRepository;
import com.itacademy.sigma_team.flowers.repositories.SqlDatabaseConnection;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;

import java.sql.SQLException;

public final class FlowerGatewayFactory {

    public static FlowerGateway getRepository() {

        return new FlowerSqlRepository();

    }


}
