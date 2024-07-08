package com.itacademy.sigma_team.flowers.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlDatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/floristeria_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    private SqlDatabaseConnection() {
        // Constructor privado para evitar instanciación
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            synchronized (SqlDatabaseConnection.class) {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                }
            }
        }
        return connection;
    }
}

