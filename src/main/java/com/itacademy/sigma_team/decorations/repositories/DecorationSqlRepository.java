package com.itacademy.sigma_team.decorations.repositories;

import com.itacademy.sigma_team.H2DatabaseConnection;
import com.itacademy.sigma_team.domain.Material;
import com.itacademy.sigma_team.decorations.use_cases.DecorationGateway;
import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.tickets.repositories.TicketSqlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DecorationSqlRepository implements DecorationGateway {

    private static final Logger logger = LoggerFactory.getLogger(DecorationSqlRepository.class);

    @Override
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
            System.out.println("Decoración insertada en la base de datos SQL");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void decrementStock(String decorationId, int quantityPurchased) {
        String sql = "UPDATE products SET stock = stock - ? WHERE id = ? AND type = 'Decoration' AND stock >= ?";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantityPurchased);
            pstmt.setString(2, decorationId);
            pstmt.setInt(3, quantityPurchased);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Stock decremented successfully for decoration with ID: " + decorationId);
            } else {
                System.out.println("Not enough stock to decrement for decoration with ID: " + decorationId);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while decrementing the stock for decoration with ID: " + decorationId, e);
        }
    }

    @Override
    public DecorationDTO get(String decorationId) {

        String sql = "SELECT * FROM products WHERE id = ? AND type = 'Decoration'";

        try (Connection conn = H2DatabaseConnection.getConnection();
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
    public Collection<DecorationDTO> getAll() {

        String sql = "SELECT * FROM products WHERE type = 'Decoration'";
        List<DecorationDTO> decorations = new ArrayList<>();

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

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
            logger.error("SQL Exception occurred while getting all Decorations", e);
        }
        return decorations;
    }

    @Override
    public void delete(DecorationDTO decorationDTO) {

        String sql = "DELETE FROM products WHERE id = ? AND type = 'Decoration'";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, decorationDTO.id());
            pstmt.executeUpdate();
            System.out.println("Decoración eliminada de la base de datos SQL");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

