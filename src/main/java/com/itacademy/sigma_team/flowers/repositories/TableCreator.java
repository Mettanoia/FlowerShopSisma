package com.itacademy.sigma_team.flowers.repositories;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class TableCreator {

    public static void createDatabaseAndTables() {
        createDatabaseIfNotExists();
        createTables();
    }

    private static void createDatabaseIfNotExists() {
        String databaseName = "floristeria_db";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            statement.executeUpdate(createDatabaseQuery);
            System.out.println("Database created or already exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String createFlowerShopTable = """
                CREATE TABLE IF NOT EXISTS FlowerShop (
                    id VARCHAR(36) PRIMARY KEY,
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
                    id VARCHAR(36) PRIMARY KEY,
                    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    product_type ENUM('tree', 'flower', 'decoration') NOT NULL,
                    product_id VARCHAR(36) NOT NULL,
                    quantity INT NOT NULL,
                    price DECIMAL(10, 2) NOT NULL
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
