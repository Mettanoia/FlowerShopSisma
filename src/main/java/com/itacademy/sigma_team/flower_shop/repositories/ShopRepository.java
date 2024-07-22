package com.itacademy.sigma_team.flower_shop.repositories;

import com.itacademy.sigma_team.H2DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class ShopRepository {

    private static final Logger logger = LoggerFactory.getLogger(ShopRepository.class);

    public void add(FlowerShopDTO dto) throws IOException {
        String sql = "INSERT INTO flowershop (id, name) VALUES (?, ?)";

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dto.id());
            pstmt.setString(2, dto.name());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            LoggerFactory.getLogger(FlowerShopSqlRepository.class).error("SQL Exception occurred while inserting Flower Shop: " + dto.id(), e);
        }
    }

    public String getShopIdByName(String shopName) {

        String sql = "SELECT id FROM FlowerShop WHERE name = ?";
        String shopId = null;

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, shopName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                shopId = rs.getString("id");
            }

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while retrieving shop ID for shop name: {}", shopName, e);
        }

        return shopId;
    }

    public List<String> getAllShopNames() {
        String sql = "SELECT name FROM FlowerShop";
        List<String> shopNames = new ArrayList<>();

        try (Connection conn = H2DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                shopNames.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while retrieving all shop names", e);
        }

        return shopNames;
    }
}
