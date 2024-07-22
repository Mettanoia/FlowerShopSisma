package com.itacademy.sigma_team.flower_shop.repositories;

import com.itacademy.sigma_team.H2DatabaseConnection;
import com.itacademy.sigma_team.flower_shop.use_cases.FlowerShopGateway;
import com.itacademy.sigma_team.tickets.repositories.TicketSqlRepository;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FlowerShopSqlRepository implements FlowerShopGateway {
    @Override
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
}
