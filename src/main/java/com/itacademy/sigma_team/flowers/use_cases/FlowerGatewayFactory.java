package com.itacademy.sigma_team.flowers.use_cases;

import com.itacademy.sigma_team.flowers.repositories.FlowerSqlRepository;
import com.itacademy.sigma_team.flowers.repositories.SqlDatabaseConnection;

import java.sql.SQLException;

public final class FlowerGatewayFactory {

    public static FlowerGateway getRepository() {

        try {
            return new FlowerSqlRepository(SqlDatabaseConnection.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO Log the error
        }

    }


}
