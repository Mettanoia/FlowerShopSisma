package com.itacademy.sigma_team.flowers.repositories;

import com.itacademy.sigma_team.H2DatabaseConnection;
import com.itacademy.sigma_team.dtos.FlowerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class FlowerSqlRepository {

    private static final Logger logger = LoggerFactory.getLogger(FlowerSqlRepository.class);

    public void add(FlowerDTO flowerDTO) {

        String sql = "INSERT INTO Products (id, name, color, height, material, price, stock, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, flowerDTO.id());
            pstmt.setString(2, flowerDTO.name());
            pstmt.setString(3, flowerDTO.color());
            pstmt.setNull(4, Types.DOUBLE);  // height
            pstmt.setNull(5, Types.VARCHAR); // material
            pstmt.setDouble(6, flowerDTO.price());
            pstmt.setInt(7, flowerDTO.stock());
            pstmt.setString(8, "Flower");    // Discriminator

            pstmt.executeUpdate();
            logger.info("Flower inserted into the SQL database: {}", flowerDTO.id());

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while inserting the flower: {}", flowerDTO.id(), e);
        }
    }

    public void addToShop(String shopId, String flowerId) {

        String sql = "INSERT INTO ShopProducts (shopId, productId) VALUES (?, ?)";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, shopId);
            pstmt.setString(2, flowerId);

            pstmt.executeUpdate();
            logger.info("Flower added to shop: {} with flower ID: {}", shopId, flowerId);

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while adding flower to shop: {} with flower ID: {}", shopId, flowerId, e);
        }

    }

    public FlowerDTO get(String flowerId, String shopName) {

        String sql = "SELECT p.id, p.name, p.color, p.price, p.stock " +
                "FROM Products p " +
                "JOIN ShopProducts sp ON p.id = sp.productId " +
                "JOIN FlowerShop fs ON sp.shopId = fs.id " +
                "WHERE p.id = ? AND p.type = 'Flower' AND fs.name = ?";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, flowerId);
            pstmt.setString(2, shopName);
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
            logger.error("SQL Exception occurred while retrieving the flower with ID: {} from shop: {}", flowerId, shopName, e);
        }
        return null;
    }

    public Collection<FlowerDTO> getAll(String shopName) {

        String sql = "SELECT p.id, p.name, p.color, p.price, p.stock " +
                "FROM Products p " +
                "JOIN ShopProducts sp ON p.id = sp.productId " +
                "JOIN FlowerShop fs ON sp.shopId = fs.id " +
                "WHERE p.type = 'Flower' AND fs.name = ?";

        List<FlowerDTO> flowers = new ArrayList<>();

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, shopName);
            ResultSet rs = pstmt.executeQuery();

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
            logger.error("SQL Exception occurred while getting all flowers from shop: {}", shopName, e);
        }

        return flowers;
    }

    public void delete(String flowerId, String shopName) {

        String sql = "DELETE FROM ShopProducts " +
                "WHERE productId = ? AND shopId = (SELECT id FROM FlowerShop WHERE name = ?)";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, flowerId);
            pstmt.setString(2, shopName);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Flower with ID: {} deleted from shop: {}", flowerId, shopName);
            } else {
                logger.warn("No flower with ID: {} found in shop: {}", flowerId, shopName);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while deleting the flower with ID: {} from shop: {}", flowerId, shopName, e);
        }
    }

}
