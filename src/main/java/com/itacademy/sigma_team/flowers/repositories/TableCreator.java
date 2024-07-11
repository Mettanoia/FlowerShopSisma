package com.itacademy.sigma_team.flowers.repositories;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public final class TableCreator {

    public static void createDatabaseAndTables() {
        createDatabaseIfNotExists();
        createTables();
    }

    private static void createDatabaseIfNotExists() {
        String databaseName = "floristeria_db";
        try (Connection connection = SqlDatabaseConnection.getInitialConnection();
             Statement statement = connection.createStatement()) {
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            statement.executeUpdate(createDatabaseQuery);
            System.out.println("Database created or already exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables() {
        try (Connection connection = SqlDatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String createFlowerShopTable = """
                CREATE TABLE IF NOT EXISTS FlowerShop (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL
                );
            """;

            String createTreeTable = """
                CREATE TABLE IF NOT EXISTS Tree (
                    id VARCHAR(36) PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    height DECIMAL(5, 2) NOT NULL,
                    price DECIMAL(10, 2) NOT NULL,
                    stock INT NOT NULL
                );
            """;

            String createFlowerTable = """
                CREATE TABLE IF NOT EXISTS Flower (
                    id VARCHAR(36) PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    color VARCHAR(50) NOT NULL,
                    price DECIMAL(10, 2) NOT NULL,
                    stock INT NOT NULL
                );
            """;

            String createDecorationTable = """
                CREATE TABLE IF NOT EXISTS Decoration (
                    id VARCHAR(36) PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    material ENUM('wood', 'plastic') NOT NULL,
                    price DECIMAL(10, 2) NOT NULL,
                    stock INT NOT NULL
                );
            """;

            String createTicketItemTable = """
                CREATE TABLE IF NOT EXISTS TicketItem (
                    ticket_id VARCHAR(36),
                    product_id VARCHAR(36),
                    product_type ENUM('tree', 'flower', 'decoration') NOT NULL,
                    quantity INT NOT NULL,
                    PRIMARY KEY (ticket_id, product_id)
                );
            """;

            statement.executeUpdate(createFlowerShopTable);
            statement.executeUpdate(createTreeTable);
            statement.executeUpdate(createFlowerTable);
            statement.executeUpdate(createDecorationTable);
            statement.executeUpdate(createTicketItemTable);

            System.out.println("Tables created successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
