package com.itacademy.sigma_team;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class H2DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:file:/home/miguel/Descargas/FloweChopSisma/src/main/resources/testdb;AUTO_SERVER=TRUE");
    }
}
