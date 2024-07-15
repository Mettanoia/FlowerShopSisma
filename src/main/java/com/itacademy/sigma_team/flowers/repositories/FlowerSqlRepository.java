package com.itacademy.sigma_team.flowers.repositories;

import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;
import com.itacademy.sigma_team.tickets.repositories.TicketSqlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class FlowerSqlRepository implements FlowerGateway {

    private static final Logger logger = LoggerFactory.getLogger(TicketSqlRepository.class);

    @Override
    public void add(FlowerDTO flowerDTO) {

        String sql = "INSERT INTO products (id, name, color, height, material, price, stock, ticketId, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, flowerDTO.id());
            pstmt.setString(2, flowerDTO.name());
            pstmt.setString(3, flowerDTO.color());
            pstmt.setNull(4, Types.DOUBLE);  // height
            pstmt.setNull(5, Types.VARCHAR); // material
            pstmt.setDouble(6, flowerDTO.price());
            pstmt.setInt(7, flowerDTO.stock());
            pstmt.setString(8, null);
            pstmt.setString(9, "Flower");    // Discriminator

            pstmt.executeUpdate();
            System.out.println("Flor insertada en la base de datos SQL");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void decrementStock(String flowerId, int quantityPurchased) {
        String sql = "UPDATE products SET stock = stock - ? WHERE id = ? AND type = 'Flower' AND stock >= ?";

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantityPurchased);
            pstmt.setString(2, flowerId);
            pstmt.setInt(3, quantityPurchased);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Stock decremented successfully for flower with ID: " + flowerId);
            } else {
                System.out.println("Not enough stock to decrement for flower with ID: " + flowerId);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while decrementing the stock for flower with ID: " + flowerId, e);
        }
    }


    @Override
    public FlowerDTO get(String flowerId) {

        String sql = "SELECT * FROM products WHERE id = ? AND type = 'Flower'";

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
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

        String sql = "SELECT * FROM products WHERE type = 'Flower'";
        List<FlowerDTO> flowers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
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
            logger.error("SQL Exception occurred while getting all Flowers", e);
        }

        return flowers;

    }

    @Override
    public void delete(FlowerDTO flowerDTO) {

        String sql = "DELETE FROM products WHERE id = ? AND type = 'Flower'";

        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, flowerDTO.id());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
