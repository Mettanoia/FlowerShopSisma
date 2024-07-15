package com.itacademy.sigma_team.testing_chuchus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class DatabaseSeeder {

    public static void seedDatabase() {
        seedFlowers();
        seedTrees();
        seedDecorations();
        seedTicketItems();
    }

    private static void seedFlowers() {
        String sql = "INSERT INTO Flower (id, name, color, price, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "1");
            pstmt.setString(2, "Rose");
            pstmt.setString(3, "Red");
            pstmt.setDouble(4, 10.5);
            pstmt.setInt(5, 100);
            pstmt.executeUpdate();

            pstmt.setString(1, "2");
            pstmt.setString(2, "Tulip");
            pstmt.setString(3, "Yellow");
            pstmt.setDouble(4, 7.0);
            pstmt.setInt(5, 200);
            pstmt.executeUpdate();

            pstmt.setString(1, "3");
            pstmt.setString(2, "Orchid");
            pstmt.setString(3, "White");
            pstmt.setDouble(4, 15.0);
            pstmt.setInt(5, 50);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void seedTrees() {
        String sql = "INSERT INTO Tree (id, name, height, price, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "1");
            pstmt.setString(2, "Oak");
            pstmt.setDouble(3, 3.2);
            pstmt.setDouble(4, 15.5);
            pstmt.setInt(5, 50);
            pstmt.executeUpdate();

            pstmt.setString(1, "2");
            pstmt.setString(2, "Elm");
            pstmt.setDouble(3, 4.2);
            pstmt.setDouble(4, 12.5);
            pstmt.setInt(5, 40);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void seedDecorations() {
        String sql = "INSERT INTO Decoration (id, name, material, price, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "1");
            pstmt.setString(2, "Wooden Statue");
            pstmt.setString(3, "wood");
            pstmt.setDouble(4, 20.0);
            pstmt.setInt(5, 30);
            pstmt.executeUpdate();

            pstmt.setString(1, "2");
            pstmt.setString(2, "Plastic Vase");
            pstmt.setString(3, "plastic");
            pstmt.setDouble(4, 5.0);
            pstmt.setInt(5, 100);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void seedTicketItems() {
        String sql = "INSERT INTO TicketItem (ticket_id, product_id, product_type, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "T1");
            pstmt.setString(2, "1");
            pstmt.setString(3, "flower");
            pstmt.setInt(4, 3);
            pstmt.executeUpdate();

            pstmt.setString(1, "T1");
            pstmt.setString(2, "2");
            pstmt.setString(3, "flower");
            pstmt.setInt(4, 5);
            pstmt.executeUpdate();

            pstmt.setString(1, "T2");
            pstmt.setString(2, "1");
            pstmt.setString(3, "tree");
            pstmt.setInt(4, 1);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

