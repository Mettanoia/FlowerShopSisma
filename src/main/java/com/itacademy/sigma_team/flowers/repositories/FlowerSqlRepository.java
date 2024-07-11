package com.itacademy.sigma_team.flowers.repositories;

import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public final class FlowerSqlRepository implements FlowerGateway {

    @Override
    public void add(FlowerDTO flowerDTO) {
        String sql = "INSERT INTO Flower (id, name, color, price, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, flowerDTO.id());
            pstmt.setString(2, flowerDTO.name());
            pstmt.setString(3, flowerDTO.color());
            pstmt.setDouble(4, flowerDTO.price());
            pstmt.setInt(5, flowerDTO.stock());
            pstmt.executeUpdate();
            System.out.println("Flor insertada en la base de datos SQL");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public FlowerDTO get(String flowerId) {
        String sql = "SELECT * FROM Flower WHERE id = ?";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, flowerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new FlowerDTO(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("color"),
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
    public Collection<FlowerDTO> getAll() {
        String sql = "SELECT * FROM Flower";
        Collection<FlowerDTO> flowers = new ArrayList<>();
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                FlowerDTO flower = new FlowerDTO(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("color"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
                flowers.add(flower);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flowers;
    }

    @Override
    public void delete(FlowerDTO flowerDTO) {
        String sql = "DELETE FROM Flower WHERE id = ?";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, flowerDTO.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
