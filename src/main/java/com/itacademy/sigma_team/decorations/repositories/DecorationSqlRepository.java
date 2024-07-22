package com.itacademy.sigma_team.decorations.repositories;

import com.itacademy.sigma_team.H2DatabaseConnection;
import com.itacademy.sigma_team.domain.Material;
import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.tickets.repositories.TicketSqlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DecorationSqlRepository {

    private static final Logger logger = LoggerFactory.getLogger(DecorationSqlRepository.class);

    public void add(DecorationDTO decorationDTO) {
        String sql = "INSERT INTO products (id, name, color, height, material, price, stock, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, decorationDTO.id());
            pstmt.setString(2, decorationDTO.name());
            pstmt.setNull(3, Types.VARCHAR); // color
            pstmt.setNull(4, Types.DOUBLE);  // height
            pstmt.setString(5, decorationDTO.material().name());
            pstmt.setDouble(6, decorationDTO.price());
            pstmt.setInt(7, decorationDTO.stock());
            pstmt.setString(8, "Decoration"); // Discriminator

            pstmt.executeUpdate();
            logger.info("Decoration inserted into SQL database");

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while inserting the decoration: " + decorationDTO.id(), e);
        }
    }

    public void addToShop(String shopId, String decorationId) {
        String sql = "INSERT INTO ShopProducts (shopId, productId) VALUES (?, ?)";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, shopId);
            pstmt.setString(2, decorationId);

            pstmt.executeUpdate();
            logger.info("Decoration added to shop: {} with decoration ID: {}", shopId, decorationId);

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while adding decoration to shop: " + shopId + " with decoration ID: " + decorationId, e);
        }
    }

    public void decrementStock(String decorationId, int quantityPurchased, String shopName) {
        String sql = "UPDATE Products p " +
                "JOIN ShopProducts sp ON p.id = sp.productId " +
                "JOIN FlowerShop fs ON sp.shopId = fs.id " +
                "SET p.stock = p.stock - ? " +
                "WHERE p.id = ? AND p.type = 'Decoration' AND fs.name = ? AND p.stock >= ?";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantityPurchased);
            pstmt.setString(2, decorationId);
            pstmt.setString(3, shopName);
            pstmt.setInt(4, quantityPurchased);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Stock decremented successfully for decoration with ID: " + decorationId);
            } else {
                logger.warn("Not enough stock to decrement for decoration with ID: " + decorationId);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while decrementing the stock for decoration with ID: " + decorationId + " in shop: " + shopName, e);
        }
    }

    public DecorationDTO get(String decorationId, String shopName) {
        String sql = "SELECT p.id, p.name, p.material, p.price, p.stock " +
                "FROM Products p " +
                "JOIN ShopProducts sp ON p.id = sp.productId " +
                "JOIN FlowerShop fs ON sp.shopId = fs.id " +
                "WHERE p.id = ? AND p.type = 'Decoration' AND fs.name = ?";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, decorationId);
            pstmt.setString(2, shopName);
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
            logger.error("SQL Exception occurred while retrieving the decoration with ID: " + decorationId + " from shop: " + shopName, e);
        }
        return null;
    }

    public Collection<DecorationDTO> getAll(String shopName) {
        String sql = "SELECT p.id, p.name, p.material, p.price, p.stock " +
                "FROM Products p " +
                "JOIN ShopProducts sp ON p.id = sp.productId " +
                "JOIN FlowerShop fs ON sp.shopId = fs.id " +
                "WHERE p.type = 'Decoration' AND fs.name = ?";
        List<DecorationDTO> decorations = new ArrayList<>();

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, shopName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DecorationDTO decoration = new DecorationDTO(
                        rs.getString("id"),
                        rs.getString("name"),
                        Material.valueOf(rs.getString("material")),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );

                decorations.add(decoration);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while getting all decorations from shop: " + shopName, e);
        }

        return decorations;
    }

    public void delete(String decorationId, String shopName) {
        String sql = "DELETE FROM ShopProducts " +
                "WHERE productId = ? AND shopId = (SELECT id FROM FlowerShop WHERE name = ?)";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, decorationId);
            pstmt.setString(2, shopName);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Decoration with ID: " + decorationId + " deleted from shop: " + shopName);
            } else {
                logger.warn("No decoration with ID: " + decorationId + " found in shop: " + shopName);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while deleting the decoration with ID: " + decorationId + " from shop: " + shopName, e);
        }
    }
}
