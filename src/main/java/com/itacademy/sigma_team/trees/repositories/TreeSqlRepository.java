package com.itacademy.sigma_team.trees.repositories;

import com.itacademy.sigma_team.H2DatabaseConnection;
import com.itacademy.sigma_team.tickets.repositories.TicketSqlRepository;
import com.itacademy.sigma_team.dtos.TreeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class TreeSqlRepository {

    private static final Logger logger = LoggerFactory.getLogger(TicketSqlRepository.class);

    public void add(TreeDTO treeDTO) {

        String sql = "INSERT INTO products (id, name, color, height, material, price, stock, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, treeDTO.id());
            pstmt.setString(2, treeDTO.name());
            pstmt.setNull(3, Types.VARCHAR);
            pstmt.setDouble(4, treeDTO.height());
            pstmt.setNull(5, Types.VARCHAR);
            pstmt.setDouble(6, treeDTO.price());
            pstmt.setInt(7, treeDTO.stock());
            pstmt.setString(8, "Tree");      // Discriminator

            pstmt.executeUpdate();
            logger.info("Tree inserted into SQL database");

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while inserting the tree: " + treeDTO.id(), e);
        }
    }

    public void addToShop(String shopId, String treeId) {

        String sql = "INSERT INTO ShopProducts (shopId, productId) VALUES (?, ?)";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, shopId);
            pstmt.setString(2, treeId);

            pstmt.executeUpdate();
            logger.info("Tree added to shop: {} with tree ID: {}", shopId, treeId);

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while adding tree to shop: " + shopId + " with tree ID: " + treeId, e);
        }
    }

    public TreeDTO get(String treeId, String shopName) {

        String sql = "SELECT p.id, p.name, p.height, p.price, p.stock " +
                "FROM Products p " +
                "JOIN ShopProducts sp ON p.id = sp.productId " +
                "JOIN FlowerShop fs ON sp.shopId = fs.id " +
                "WHERE p.id = ? AND p.type = 'Tree' AND fs.name = ?";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, treeId);
            pstmt.setString(2, shopName);
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
            logger.error("SQL Exception occurred while retrieving the tree with ID: " + treeId + " from shop: " + shopName, e);
        }
        return null;
    }

    public Collection<TreeDTO> getAll(String shopName) {

        String sql = "SELECT p.id, p.name, p.height, p.price, p.stock " +
                "FROM Products p " +
                "JOIN ShopProducts sp ON p.id = sp.productId " +
                "JOIN FlowerShop fs ON sp.shopId = fs.id " +
                "WHERE p.type = 'Tree' AND fs.name = ?";

        List<TreeDTO> trees = new ArrayList<>();

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, shopName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                TreeDTO tree = new TreeDTO(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("height"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );

                trees.add(tree);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while getting all trees from shop: " + shopName, e);
        }

        return trees;
    }

    public void delete(String treeId, String shopName) {

        String sql = "DELETE FROM ShopProducts " +
                "WHERE productId = ? AND shopId = (SELECT id FROM FlowerShop WHERE name = ?)";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, treeId);
            pstmt.setString(2, shopName);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Tree with ID: " + treeId + " deleted from shop: " + shopName);
            } else {
                logger.warn("No tree with ID: " + treeId + " found in shop: " + shopName);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while deleting the tree with ID: " + treeId + " from shop: " + shopName, e);
        }
    }
}
