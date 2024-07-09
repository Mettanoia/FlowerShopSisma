package com.itacademy.sigma_team.trees.repositories;

import com.itacademy.sigma_team.flowers.repositories.SqlDatabaseConnection;
import com.itacademy.sigma_team.gateways.TreeGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class TreeSqlRepository implements TreeGateway {

    @Override
    public void addTree(TreeDTO treeDTO) {
        String sql = "INSERT INTO Tree (id, name, height, price, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, treeDTO.id());
            pstmt.setString(2, treeDTO.name());
            pstmt.setDouble(3, treeDTO.height());
            pstmt.setDouble(4, treeDTO.price());
            pstmt.setInt(5, treeDTO.stock());
            pstmt.executeUpdate();
            System.out.println("Árbol insertado en la base de datos SQL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TreeDTO getTree(String treeId) {
        String sql = "SELECT * FROM Tree WHERE id = ?";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, treeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new TreeDTO(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("height"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteTree(TreeDTO treeDTO) {
        String sql = "DELETE FROM Tree WHERE id = ?";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, treeDTO.id());
            pstmt.executeUpdate();
            System.out.println("Árbol eliminado de la base de datos SQL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


