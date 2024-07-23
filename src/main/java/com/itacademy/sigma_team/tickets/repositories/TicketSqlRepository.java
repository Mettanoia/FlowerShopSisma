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

        String updateTicketProductSql = "UPDATE TicketProducts SET quantity =  ? WHERE ticketId = ? AND productId = ?";

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

            Map<String, Integer> productQuantities = dto.items().stream()
                    .collect(Collectors.toMap(TicketItem::getId, item -> 1, Integer::sum));

            for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
                String productId = entry.getKey();
                int quantity = entry.getValue();

                logger.debug("Processing product ID: {} with quantity: {}", productId, quantity);

                // Update the product stock
                updateProductStockStmt.setInt(1, quantity);
                updateProductStockStmt.setString(2, productId);
                updateProductStockStmt.setInt(3, quantity);
                updateProductStockStmt.setString(4, shopName);

                logger.debug("Executing stock update for product ID: {} with quantity: {}", productId, quantity);

                int rowsAffected = updateProductStockStmt.executeUpdate();

                if (rowsAffected == 0) {
                    logger.warn("Not enough stock to decrement for product with ID: {}", productId);
                    connection.rollback();
                    return;
                } else {
                    logger.info("Stock updated for product ID: {}, decremented by quantity: {}", productId, quantity);
                }

                // Update the ticket products
                updateTicketProductStmt.setInt(1, quantity);
                updateTicketProductStmt.setString(2, dto.id());
                updateTicketProductStmt.setString(3, productId);

                logger.debug("Executing ticket product update for ticket ID: {}, product ID: {}", dto.id(), productId);

                int updateRows = updateTicketProductStmt.executeUpdate();

                if (updateRows > 0) {
                    logger.info("Updated TicketProducts for ticket ID: {}, product ID: {}, new quantity: {}", dto.id(), productId, quantity);
                } else {
                    logger.debug("No existing record found for ticket ID: {}, product ID: {}, inserting new record", dto.id(), productId);

                    insertTicketProductStmt.setString(1, dto.id());
                    insertTicketProductStmt.setString(2, productId);
                    insertTicketProductStmt.setInt(3, quantity);
                    insertTicketProductStmt.executeUpdate();

                    logger.info("Inserted into TicketProducts for ticket ID: {}, product ID: {}, quantity: {}", dto.id(), productId, quantity);
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

        String SELECT_ITEMS_SQL = "SELECT tp.productId, p.name, p.color, p.height, p.material, p.price, p.stock, p.type, tp.quantity " +
                "FROM TicketProducts tp " +
                "JOIN products p ON tp.productId = p.id " +
                "WHERE tp.ticketId = ?";

        try (Connection connection = H2DatabaseConnection.getConnection();
             PreparedStatement ticketStmt = connection.prepareStatement(SELECT_TICKET_SQL);
             PreparedStatement itemsStmt = connection.prepareStatement(SELECT_ITEMS_SQL)) {

            logger.debug("Executing SELECT_TICKET_SQL for ticket ID: {}", id);
            ticketStmt.setString(1, id);
            try (ResultSet ticketRs = ticketStmt.executeQuery()) {
                if (ticketRs.next()) {
                    String ticketId = ticketRs.getString("id");
                    LocalDateTime dateTime = ticketRs.getTimestamp("dateTime").toLocalDateTime();
                    String shopName = ticketRs.getString("shopName");
                    List<TicketItem> items = new ArrayList<>();

                    logger.debug("Ticket found with ID: {}, DateTime: {}, ShopName: {}", ticketId, dateTime, shopName);

                    itemsStmt.setString(1, id);
                    logger.debug("Executing SELECT_ITEMS_SQL for ticket ID: {}", id);
                    try (ResultSet itemsRs = itemsStmt.executeQuery()) {
                        while (itemsRs.next()) {
                            String itemId = itemsRs.getString("productId");
                            String itemName = itemsRs.getString("name");
                            String itemColor = itemsRs.getString("color");
                            double itemHeight = itemsRs.getDouble("height");
                            String itemMaterial = itemsRs.getString("material");
                            double itemPrice = itemsRs.getDouble("price");
                            int itemStock = itemsRs.getInt("stock");
                            String itemType = itemsRs.getString("type");
                            int quantity = itemsRs.getInt("quantity");

                            logger.debug("Processing item: ID: {}, Name: {}, Type: {}, Quantity: {}", itemId, itemName, itemType, quantity);

                            for (int i = 0; i < quantity; i++) {
                                TicketItem itemDTO;
                                switch (itemType) {
                                    case "Flower" -> itemDTO = new FlowerDTO(itemId, itemName, itemColor, itemPrice, itemStock);
                                    case "Tree" -> itemDTO = new TreeDTO(itemId, itemName, itemHeight, itemPrice, itemStock);
                                    case "Decoration" -> itemDTO = new DecorationDTO(itemId, itemName, Material.valueOf(itemMaterial), itemPrice, itemStock);
                                    default -> {
                                        logger.warn("Unsupported item type found in database: {}", itemType);
                                        continue;
                                    }
                                }
                                items.add(itemDTO);
                            }
                        }
                    }

                    logger.info("Returning TicketDTO for ticket ID: {} with items: {}", ticketId, items);
                    return new TicketDTO(ticketId, dateTime, items);

                } else {
                    logger.warn("Ticket with ID: {} not found", id);
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching ticket with ID: {}", id, e);
            return null;
        }
    }


    public Collection<TicketDTO> getAll(String shopName) {

        String SELECT_TICKETS_FOR_SHOP = "SELECT t.id AS ticketId, t.dateTime AS ticketDateTime, fs.name AS shopName " +
                "FROM tickets t " +
                "JOIN ShopTickets st ON t.id = st.ticketId " +
                "JOIN FlowerShop fs ON st.shopId = fs.id " +
                "WHERE fs.name = ?";

        String SELECT_PRODUCTS_FOR_TICKET = "SELECT tp.productId AS productId, p.name AS productName, p.color AS productColor, " +
                "p.height AS productHeight, p.material AS productMaterial, p.price AS productPrice, p.stock AS productStock, " +
                "p.type AS productType, tp.quantity AS productQuantity " +
                "FROM TicketProducts tp " +
                "JOIN products p ON tp.productId = p.id " +
                "WHERE tp.ticketId = ?";

        List<TicketDTO> tickets = new ArrayList<>();

        try (Connection connection = H2DatabaseConnection.getConnection();
             PreparedStatement ticketsStmt = connection.prepareStatement(SELECT_TICKETS_FOR_SHOP);
             PreparedStatement productsStmt = connection.prepareStatement(SELECT_PRODUCTS_FOR_TICKET)) {

            ticketsStmt.setString(1, shopName);
            logger.debug("Executing SELECT_TICKETS_FOR_SHOP for shop name: {}", shopName);
            try (ResultSet ticketsRs = ticketsStmt.executeQuery()) {

                while (ticketsRs.next()) {
                    String ticketId = ticketsRs.getString("ticketId");
                    LocalDateTime ticketDateTime = ticketsRs.getTimestamp("ticketDateTime").toLocalDateTime();
                    List<TicketItem> ticketItems = new ArrayList<>();

                    productsStmt.setString(1, ticketId);
                    logger.debug("Executing SELECT_PRODUCTS_FOR_TICKET for ticket ID: {}", ticketId);
                    try (ResultSet productsRs = productsStmt.executeQuery()) {

                        while (productsRs.next()) {
                            String productId = productsRs.getString("productId");
                            String productName = productsRs.getString("productName");
                            String productColor = productsRs.getString("productColor");
                            double productHeight = productsRs.getDouble("productHeight");
                            String productMaterial = productsRs.getString("productMaterial");
                            double productPrice = productsRs.getDouble("productPrice");
                            int productStock = productsRs.getInt("productStock");
                            String productType = productsRs.getString("productType");
                            int productQuantity = productsRs.getInt("productQuantity");

                            logger.debug("Processing product: ID: {}, Name: {}, Type: {}, Quantity: {}", productId, productName, productType, productQuantity);

                            for (int i = 0; i < productQuantity; i++) {
                                TicketItem ticketItem;
                                switch (productType) {
                                    case "Flower" ->
                                            ticketItem = new FlowerDTO(productId, productName, productColor, productPrice, productStock);
                                    case "Tree" ->
                                            ticketItem = new TreeDTO(productId, productName, productHeight, productPrice, productStock);
                                    case "Decoration" ->
                                            ticketItem = new DecorationDTO(productId, productName, Material.valueOf(productMaterial), productPrice, productStock);
                                    default -> {
                                        logger.warn("Unsupported product type found in database: {}", productType);
                                        continue;
                                    }
                                }
                                ticketItems.add(ticketItem);
                            }
                        }
                    }

                    TicketDTO ticketDTO = new TicketDTO(ticketId, ticketDateTime, ticketItems);
                    tickets.add(ticketDTO);
                    logger.info("Added TicketDTO for ticket ID: {} with items: {}", ticketId, ticketItems);
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
