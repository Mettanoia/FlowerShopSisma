package com.itacademy.sigma_team.trees.repositories;

import com.itacademy.sigma_team.tickets.repositories.TicketSqlRepository;
import com.itacademy.sigma_team.trees.use_cases.TreeGateway;
import com.itacademy.sigma_team.dtos.TreeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class TreeSqlRepository implements TreeGateway {

    private static final Logger logger = LoggerFactory.getLogger(TicketSqlRepository.class);

    @Override
    public void add(TreeDTO treeDTO) {

        String sql = "INSERT INTO products (id, name, color, height, material, price, stock, ticketId, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, treeDTO.id());
            pstmt.setString(2, treeDTO.name());
            pstmt.setNull(3, Types.VARCHAR); // color
            pstmt.setDouble(4, treeDTO.height());
            pstmt.setNull(5, Types.VARCHAR); // material
            pstmt.setDouble(6, treeDTO.price());
            pstmt.setInt(7, treeDTO.stock());
            pstmt.setString(8, null);
            pstmt.setString(9, "Tree");      // Discriminator

            pstmt.executeUpdate();
            System.out.println("Tree inserted into SQL database");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void decrementStock(String treeId, int quantityPurchased) {
        String sql = "UPDATE products SET stock = stock - ? WHERE id = ? AND type = 'Tree' AND stock >= ?";

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantityPurchased);
            pstmt.setString(2, treeId);
            pstmt.setInt(3, quantityPurchased);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Stock decremented successfully for tree with ID: " + treeId);
            } else {
                System.out.println("Not enough stock to decrement for tree with ID: " + treeId);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while decrementing the stock for tree with ID: " + treeId, e);
        }
    }

    @Override
    public TreeDTO get(String treeId) {

        String sql = "SELECT * FROM products WHERE id = ? AND type = 'Tree'";

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
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
    public Collection<TreeDTO> getAll() {

        String sql = "SELECT * FROM products WHERE type = 'Tree'";
        List<TreeDTO> trees = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

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
            logger.error("SQL Exception occurred while getting all Trees", e);
        }

        return trees;
    }


    @Override
    public void delete(TreeDTO treeDTO) {

        String sql = "DELETE FROM products WHERE id = ? AND type = 'Tree'";

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, treeDTO.id());
            pstmt.executeUpdate();
            System.out.println("Tree deleted from SQL database");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    // New method to decrement stock
    public void decrementStock(String treeId, int quantityPurchased) {
        String sql = "UPDATE products SET stock = stock - ? WHERE id = ? AND type = 'Tree' AND stock >= ?";

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantityPurchased);
            pstmt.setString(2, treeId);
            pstmt.setInt(3, quantityPurchased);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Stock decremented successfully for tree with ID: " + treeId);
            } else {
                System.out.println("Not enough stock to decrement for tree with ID: " + treeId);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while decrementing the stock for tree with ID: " + treeId, e);
        }
    }


}


