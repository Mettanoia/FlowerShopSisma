package com.itacademy.sigma_team.tickets.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class H2DatabaseConnection {

    private static final String JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

}
