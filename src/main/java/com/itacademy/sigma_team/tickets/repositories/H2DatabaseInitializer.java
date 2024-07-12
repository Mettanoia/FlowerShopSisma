package com.itacademy.sigma_team.tickets.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class H2DatabaseInitializer {

    public static void initializeDatabase() throws SQLException {
        try (Connection connection = H2DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            // Create tables
            String createTicketTable = "CREATE TABLE TicketDTO (" +
                    "id VARCHAR(255) PRIMARY KEY);";
            statement.execute(createTicketTable);

            String createFlowerTable = "CREATE TABLE FlowerDTO (" +
                    "id VARCHAR(255), " +
                    "name VARCHAR(255), " +
                    "color VARCHAR(255), " +
                    "price DOUBLE, " +
                    "stock INT, " +
                    "PRIMARY KEY (id));";
            statement.execute(createFlowerTable);

            String createTreeTable = "CREATE TABLE TreeDTO (" +
                    "id VARCHAR(255), " +
                    "species VARCHAR(255), " +
                    "height DOUBLE, " +
                    "price DOUBLE, " +
                    "PRIMARY KEY (id));";
            statement.execute(createTreeTable);

            String createDecorationTable = "CREATE TABLE DecorationDTO (" +
                    "id INT, " +
                    "material VARCHAR(255), " +
                    "PRIMARY KEY (id));";
            statement.execute(createDecorationTable);
        }
    }
}
