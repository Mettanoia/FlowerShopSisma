package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.H2DatabaseConnection;
import com.itacademy.sigma_team.domain.Material;
import com.itacademy.sigma_team.dtos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public final class TicketSqlRepository {

    private static final Logger logger = LoggerFactory.getLogger(TicketSqlRepository.class);

    public void add(TicketDTO dto, String shopName) {

        String insertTicketSql = "INSERT INTO tickets (id, dateTime) VALUES (?, ?)";

        String insertShopTicketSql = "INSERT INTO ShopTickets (shopId, ticketId) VALUES ((SELECT id FROM FlowerShop WHERE name = ?), ?)";

        String insertTicketProductSql = "INSERT INTO TicketProducts (ticketId, productId, quantity) VALUES (?, ?, ?)";

        String updateTicketProductSql = "UPDATE TicketProducts SET quantity = quantity + ? WHERE ticketId = ? AND productId = ?";

        String updateProductStockSql = "UPDATE Products SET stock = stock - ? " +
                "WHERE id = ? AND stock >= ? AND id IN (SELECT productId FROM ShopProducts WHERE shopId = (SELECT id FROM FlowerShop WHERE name = ?))";

        if (dto.items().isEmpty()) {
            logger.warn("Ticket with ID: {} has no items.", dto.id());
            return;
        }

        try (Connection connection = H2DatabaseConnection.getConnection();
             PreparedStatement insertTicketStmt = connection.prepareStatement(insertTicketSql);
             PreparedStatement insertShopTicketStmt = connection.prepareStatement(insertShopTicketSql);
             PreparedStatement insertTicketProductStmt = connection.prepareStatement(insertTicketProductSql);
             PreparedStatement updateTicketProductStmt = connection.prepareStatement(updateTicketProductSql);
             PreparedStatement updateProductStockStmt = connection.prepareStatement(updateProductStockSql)) {

            connection.setAutoCommit(false);

            insertTicketStmt.setString(1, dto.id());
            insertTicketStmt.setObject(2, dto.dateTime());
            insertTicketStmt.executeUpdate();

            insertShopTicketStmt.setString(1, shopName);
            insertShopTicketStmt.setString(2, dto.id());
            insertShopTicketStmt.executeUpdate();

            Map<String, List<TicketItem>> groupedItems = dto.items().stream()
                    .collect(Collectors.groupingBy(TicketItem::getId));

            for (Map.Entry<String, List<TicketItem>> entry : groupedItems.entrySet()) {

                String itemId = entry.getKey();
                int quantity = entry.getValue().size();

                updateProductStockStmt.setInt(1, quantity);
                updateProductStockStmt.setString(2, itemId);
                updateProductStockStmt.setInt(3, quantity);
                updateProductStockStmt.setString(4, shopName);

                int rowsAffected = updateProductStockStmt.executeUpdate();

                if (rowsAffected == 0) {
                    logger.warn("Not enough stock to decrement for product with ID: {}", itemId);
                    connection.rollback();
                    return;
                }

                updateTicketProductStmt.setInt(1, quantity);
                updateTicketProductStmt.setString(2, dto.id());
                updateTicketProductStmt.setString(3, itemId);
                int updateRows = updateTicketProductStmt.executeUpdate();

                if (updateRows > 0) {
                    logger.info("Updated TicketProducts for ticket ID: {}, product ID: {}, new quantity: {}", dto.id(), itemId, quantity);
                } else {
                    insertTicketProductStmt.setString(1, dto.id());
                    insertTicketProductStmt.setString(2, itemId);
                    insertTicketProductStmt.setInt(3, quantity);
                    insertTicketProductStmt.executeUpdate();
                    logger.info("Inserted into TicketProducts for ticket ID: {}, product ID: {}, quantity: {}", dto.id(), itemId, quantity);
                }
            }

            connection.commit();
            logger.info("Transaction committed successfully for Ticket: {}", dto.id());

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while adding Ticket: {}", dto.id(), e);
            rollback();
        }
    }

    public void delete(TicketDTO dto) {
        String deleteItemsSql = "DELETE FROM TicketProducts WHERE ticketId = ?";
        String deleteTicketSql = "DELETE FROM tickets WHERE id = ?";
        String deleteShopTicketSql = "DELETE FROM ShopTickets WHERE ticketId = ?";

        try (Connection connection = H2DatabaseConnection.getConnection();
             PreparedStatement deleteItemsStmt = connection.prepareStatement(deleteItemsSql);
             PreparedStatement deleteTicketStmt = connection.prepareStatement(deleteTicketSql);
             PreparedStatement deleteShopTicketStmt = connection.prepareStatement(deleteShopTicketSql)) {

            connection.setAutoCommit(false);

            deleteItemsStmt.setString(1, dto.id());
            deleteItemsStmt.executeUpdate();

            deleteShopTicketStmt.setString(1, dto.id());
            deleteShopTicketStmt.executeUpdate();

            deleteTicketStmt.setString(1, dto.id());
            deleteTicketStmt.executeUpdate();

            connection.commit();
            logger.info("Transaction committed successfully for deleting Ticket: {}", dto.id());

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while deleting Ticket: {}", dto.id(), e);
            rollback();
        }
    }

    public TicketDTO get(String id) {
        String SELECT_TICKET_SQL = "SELECT t.id, t.dateTime, fs.name AS shopName " +
                "FROM tickets t " +
                "JOIN ShopTickets st ON t.id = st.ticketId " +
                "JOIN FlowerShop fs ON st.shopId = fs.id " +
                "WHERE t.id = ?";
        String SELECT_ITEMS_SQL = "SELECT * FROM TicketProducts tp " +
                "JOIN products p ON tp.productId = p.id " +
                "WHERE tp.ticketId = ?";

        try (Connection connection = H2DatabaseConnection.getConnection();
             PreparedStatement ticketStmt = connection.prepareStatement(SELECT_TICKET_SQL);
             PreparedStatement itemsStmt = connection.prepareStatement(SELECT_ITEMS_SQL)) {

            ticketStmt.setString(1, id);
            try (ResultSet ticketRs = ticketStmt.executeQuery()) {
                if (ticketRs.next()) {

                    String ticketId = ticketRs.getString("id");
                    LocalDateTime dateTime = ticketRs.getTimestamp("dateTime").toLocalDateTime();
                    String shopName = ticketRs.getString("shopName");
                    Map<TicketItem, Integer> items = new HashMap<>();

                    itemsStmt.setString(1, id);
                    try (ResultSet itemsRs = itemsStmt.executeQuery()) {

                        while (itemsRs.next()) {

                            String itemId = itemsRs.getString("id");
                            String itemName = itemsRs.getString("name");
                            String itemColor = itemsRs.getString("color");
                            double itemHeight = itemsRs.getDouble("height");
                            String itemMaterial = itemsRs.getString("material");
                            double itemPrice = itemsRs.getDouble("price");
                            int itemStock = itemsRs.getInt("stock");
                            String itemType = itemsRs.getString("type");
                            int quantity = itemsRs.getInt("quantity");

                            TicketItem itemDTO;
                            switch (itemType) {
                                case "Flower":
                                    itemDTO = new FlowerDTO(itemId, itemName, itemColor, itemPrice, itemStock);
                                    break;
                                case "Tree":
                                    itemDTO = new TreeDTO(itemId, itemName, itemHeight, itemPrice, itemStock);
                                    break;
                                case "Decoration":
                                    itemDTO = new DecorationDTO(itemId, itemName, Material.valueOf(itemMaterial), itemPrice, itemStock);
                                    break;
                                default:
                                    logger.warn("Unsupported item type found in database");
                                    continue;
                            }

                            items.put(itemDTO, quantity);
                        }
                    }

                    return new TicketDTO(ticketId, dateTime, items.keySet());

                } else {
                    logger.warn("Ticket with id {} not found", id);
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching ticket with id {}", id, e);
            return null;
        }
    }

    public Collection<TicketDTO> getAll(String shopName) {

        String SELECT_TICKETS_SQL = "SELECT t.id, t.dateTime, fs.name AS shopName " +
                "FROM tickets t " +
                "JOIN ShopTickets st ON t.id = st.ticketId " +
                "JOIN FlowerShop fs ON st.shopId = fs.id " +
                "WHERE fs.name = ?";

        String SELECT_ITEMS_SQL = "SELECT * FROM TicketProducts tp " +
                "JOIN products p ON tp.productId = p.id " +
                "WHERE tp.ticketId = ?";

        List<TicketDTO> tickets = new ArrayList<>();

        try (Connection connection = H2DatabaseConnection.getConnection();
             PreparedStatement ticketsStmt = connection.prepareStatement(SELECT_TICKETS_SQL);
             PreparedStatement itemsStmt = connection.prepareStatement(SELECT_ITEMS_SQL)) {

            ticketsStmt.setString(1, shopName);
            try (ResultSet ticketsRs = ticketsStmt.executeQuery()) {

                while (ticketsRs.next()) {
                    String ticketId = ticketsRs.getString("id");
                    LocalDateTime dateTime = ticketsRs.getTimestamp("dateTime").toLocalDateTime();
                    Map<TicketItem, Integer> items = new HashMap<>();

                    itemsStmt.setString(1, ticketId);
                    try (ResultSet itemsRs = itemsStmt.executeQuery()) {

                        while (itemsRs.next()) {
                            String itemId = itemsRs.getString("id");
                            String itemName = itemsRs.getString("name");
                            String itemColor = itemsRs.getString("color");
                            double itemHeight = itemsRs.getDouble("height");
                            String itemMaterial = itemsRs.getString("material");
                            double itemPrice = itemsRs.getDouble("price");
                            int itemStock = itemsRs.getInt("stock");
                            String itemType = itemsRs.getString("type");
                            int quantity = itemsRs.getInt("quantity");

                            TicketItem itemDTO;
                            switch (itemType) {
                                case "Flower":
                                    itemDTO = new FlowerDTO(itemId, itemName, itemColor, itemPrice, itemStock);
                                    break;
                                case "Tree":
                                    itemDTO = new TreeDTO(itemId, itemName, itemHeight, itemPrice, itemStock);
                                    break;
                                case "Decoration":
                                    itemDTO = new DecorationDTO(itemId, itemName, Material.valueOf(itemMaterial), itemPrice, itemStock);
                                    break;
                                default:
                                    logger.warn("Unsupported item type found in database");
                                    continue;
                            }

                            items.put(itemDTO, quantity);
                        }
                    }

                    TicketDTO ticketDTO = new TicketDTO(ticketId, dateTime, items.keySet());
                    tickets.add(ticketDTO);
                }
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching all tickets for shop: {}", shopName, e);
        }
        return tickets;
    }


    private void rollback() {
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            if (connection != null && !connection.getAutoCommit()) {
                connection.rollback();
                logger.info("Transaction rolled back successfully.");
            }
        } catch (SQLException rollbackEx) {
            logger.error("Failed to rollback transaction", rollbackEx);
        }
    }
}
