package com.itacademy.sigma_team.decorations.repositories;

import com.itacademy.sigma_team.domain.Material;
import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.flowers.repositories.SqlDatabaseConnection;
import com.itacademy.sigma_team.gateways.DecorationGateway;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DecorationSqlRepository implements DecorationGateway {

    @Override
    public void addDecoration(DecorationDTO decorationDTO) {
        String sql = "INSERT INTO Decoration (id, name, material, price, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, decorationDTO.id());
            pstmt.setString(2, decorationDTO.name());
            pstmt.setString(3, decorationDTO.material().name());
            pstmt.setDouble(4, decorationDTO.price());
            pstmt.setInt(5, decorationDTO.stock());
            pstmt.executeUpdate();
            System.out.println("Decoración insertada en la base de datos SQL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DecorationDTO getDecoration(String decorationId) {
        String sql = "SELECT * FROM Decoration WHERE id = ?";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, decorationId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new DecorationDTO(
                        rs.getString("id"),
                        rs.getString("name"),
                        Material.valueOf(rs.getString("material")),
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
    public void deleteDecoration(DecorationDTO decorationDTO) {
        String sql = "DELETE FROM Decoration WHERE id = ?";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, decorationDTO.id());
            pstmt.executeUpdate();
            System.out.println("Decoración eliminada de la base de datos SQL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

