package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.domain.TicketItem;
import com.itacademy.sigma_team.flowers.repositories.SqlDatabaseConnection;
import com.itacademy.sigma_team.services.TicketRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class TicketSqlRepository implements TicketRepository {

    @Override
    public void saveItem(TicketItem item) {
        String sql = "INSERT INTO TicketItem (ticket_id, product_id, product_type, quantity, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.ticketId());
            pstmt.setString(2, item.productId());
            pstmt.setString(3, item.productType());
            pstmt.setInt(4, item.quantity());
            pstmt.setDouble(5, item.price());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TicketItem> findItemsByTicketId(String ticketId) {
        List<TicketItem> items = new ArrayList<>();
        String sql = "SELECT * FROM TicketItem WHERE ticket_id = ?";
        try (Connection conn = SqlDatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ticketId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TicketItem item = new TicketItem(
                        rs.getString("ticket_id"),
                        rs.getString("product_id"),
                        rs.getString("product_type"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}


}

