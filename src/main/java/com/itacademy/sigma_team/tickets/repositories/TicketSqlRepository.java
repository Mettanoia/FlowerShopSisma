package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.H2DatabaseConnection;
import com.itacademy.sigma_team.domain.Material;
import com.itacademy.sigma_team.dtos.*;
import com.itacademy.sigma_team.tickets.use_cases.TicketGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public final class TicketSqlRepository implements TicketGateway {

    private static final Logger logger = LoggerFactory.getLogger(TicketSqlRepository.class);

    @Override
    public void add(TicketDTO dto) {

        String insertTicketSql = "INSERT INTO tickets (id, dateTime) VALUES (?, ?)";

        String insertTicketProductSql = "INSERT INTO TicketProducts (ticketId, productId, quantity) VALUES (?, ?, ?)";

        String updateTicketProductSql = "UPDATE TicketProducts SET quantity = quantity + ? WHERE ticketId = ? AND productId = ?";

        String updateProductStockSql = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";

        // Check if the ticket is empty
        if (dto.items().isEmpty()) {
            displayCreepyMessage();
            return;
        }

        try (Connection connection = H2DatabaseConnection.getConnection();
             PreparedStatement insertTicketStmt = connection.prepareStatement(insertTicketSql);
             PreparedStatement insertTicketProductStmt = connection.prepareStatement(insertTicketProductSql);
             PreparedStatement updateTicketProductStmt = connection.prepareStatement(updateTicketProductSql);
             PreparedStatement updateProductStockStmt = connection.prepareStatement(updateProductStockSql)) {

            // Start transaction
            connection.setAutoCommit(false);

            // Insert TicketDTO
            logger.info("Inserting ticket with ID: {}", dto.id());
            insertTicketStmt.setString(1, dto.id());
            insertTicketStmt.setObject(2, dto.dateTime());
            insertTicketStmt.executeUpdate();

            // Count the items with the same ID
            Map<String, List<TicketItem>> groupedItems = dto.items().stream()
                    .collect(Collectors.groupingBy(TicketItem::getId));

            // Update all the TicketItems and decrement stock
            for (Map.Entry<String, List<TicketItem>> entry : groupedItems.entrySet()) {

                String itemId = entry.getKey();
                int quantity = entry.getValue().size();

                // Decrement stock
                updateProductStockStmt.setInt(1, quantity);
                updateProductStockStmt.setString(2, itemId);
                updateProductStockStmt.setInt(3, quantity);

                int rowsAffected = updateProductStockStmt.executeUpdate();

                if (rowsAffected == 0) {
                    logger.warn("Not enough stock to decrement for product with ID: {}", itemId);
                    connection.rollback();
                    return;
                }

                // Try to update the existing entry in TicketProducts
                updateTicketProductStmt.setInt(1, quantity);
                updateTicketProductStmt.setString(2, dto.id());
                updateTicketProductStmt.setString(3, itemId);
                int updateRows = updateTicketProductStmt.executeUpdate();

                if (updateRows > 0) {
                    logger.info("Updated TicketProducts for ticket ID: {}, product ID: {}, new quantity: {}", dto.id(), itemId, quantity);
                } else {
                    // If no rows were updated, insert a new entry
                    insertTicketProductStmt.setString(1, dto.id());
                    insertTicketProductStmt.setString(2, itemId);
                    insertTicketProductStmt.setInt(3, quantity);
                    insertTicketProductStmt.executeUpdate();
                    logger.info("Inserted into TicketProducts for ticket ID: {}, product ID: {}, quantity: {}", dto.id(), itemId, quantity);
                }
            }

            // Commit the transaction
            connection.commit();
            logger.info("Transaction committed successfully for Ticket: {}", dto.id());

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while adding Ticket: {}", dto.id(), e);
            rollback();
        }
    }






    private void displayCreepyMessage() {
        String creepyMessage =
                """
                        █▀▀█ █▀▀█ █▀▀▄ █▀▀█  █▀▀█ █▀▀█ █▀▀▄ █▀▀█ █▀▀█ █▀▀█
                        ░▄▄█ █░░█ █▀▀▄ █▄▄█  █▄▄▀ █▄▄█ █▀▀▄ █▄▄▀ █▄▄█ █░░█
                        █▄▄█ ▀▀▀▀ ▀▀▀░ ▀░░▀  ▀░▀▀ ▀░░▀ ▀▀▀░ ▀░▀▀ ▀░░▀ ▀▀▀▀
                        """;
        String[] spanishLines = {
                "Ṁ̴͓̑u̵͖̚c̶̫̃h̶̨̊o̸̰̓ ̵͉͋p̶̝̌ṙ̷͙o̶̤͑g̸̤̑r̴̤͌a̶̞̐m̴̯̏a̴͕͝r̷͔̀ ̸̛̫y̶̹̋ ̶̹̋p̷͍̋o̸̪͝c̶̞̋o̴̢̚ ̵̡́j̸͉̅u̵͙̍g̶̠̈́a̴̞͒r̸̹̚ ̶̡͘ȟ̷̠a̷̛̤c̴̤̒ė̸̹n̴̂ͅ ̴̖̄̕d̷̳̆͘e̴̖̿͛ ̶̪̍̍m̸̖̉̇a̴̡͂ȑ̷͇ì̸̡a̸͔̿ ̴͍͛r̶̬̈́ȍ̵̪m̶̫̕ỉ̴͔n̸͉̿ä̵̩́ ̸̢̂ủ̷̲ń̸͙a̸̟̐ ̸̯͛c̸̼̍h̶̟̀i̶͖̅c̵̼̉a̴̱̓ ̷̜̉ã̷͇b̸̠͝u̷̟͆r̶̠̃r̴͉͝i̶̼̎d̸̘͠a̴̖͌.",
                "Ṁ̴͓̑u̵͖̚c̶̫̃h̶̨̊o̸̰̓ ̵͉͋p̶̝̌ṙ̷͙o̶̤͑g̸̤̑r̴̤͌a̶̞̐m̴̯̏a̴͕͝r̷͔̀ ̸̛̫y̶̹̋ ̶̹̋p̷͍̋o̸̪͝c̶̞̋o̴̢̚ ̵̡́j̸͉̅u̵͙̍g̶̠̈́a̴̞͒r̸̹̚ ̶̡͘ȟ̷̠a̷̛̤c̴̤̒ė̸̹n̴̂ͅ ̴̄̕d̷̳̆͘e̴̖̿͛ ̶̪̍̍m̸̖̉̇a̴̡͂ȑ̷͇ì̸̡a̸͔̿ ̴͍͛r̶̬̈́ȍ̵̪m̶̫̕ỉ̴͔n̸͉̿ä̵̩́ ̸̢̂ủ̷̲ń̸͙a̸̟̐ ̸̯͛c̸̼̍h̶̟̀i̶͖̅c̵̼̉a̴̱̓ ̷̜̉ã̷͇b̸̠͝u̷̟͆r̶̠̃r̴͉͝i̶̼̎d̸̘͠a̴̖͌.",
                "Ṁ̴͓̑u̵͖̚c̶̫̃h̶̨̊o̸̰̓ ̵͉͋p̶̝̌ṙ̷͙o̶̤͑g̸̤̑r̴̤͌a̶̞̐m̴̯̏a̴͕͝r̷͔̀ ̸̛̫y̶̹̋ ̶̹̋p̷͍̋o̸̪͝c̶̞̋o̴̢̚ ̵̡́j̸͉̅u̵͙̍g̶̠̈́a̴̞͒r̸̹̚ ̶̡͘ȟ̷̠a̷̛̤c̴̤̒ė̸̹n̴̂ͅ ̴̄̕d̷̳̆͘e̴̖̿͛ ̶̪̍̍m̸̖̉̇a̴̡͂ȑ̷͇ì̸̡a̸͔̿ ̴͍͛r̶̬̈́ȍ̵̪m̶̫̕ỉ̴͔n̸͉̿ä̵̩́ ̸̢̂ủ̷̲ń̸͙a̸̟̐ ̸̯͛c̸̼̍h̶̟̀i̶͖̅c̵̼̉a̴̱̓ ̷̜̉ã̷͇b̸̠͝u̷̟͆r̶̠̃r̴͉͝i̶̼̎d̸̘͠a̴̖͌."
        };

        Random random = new Random();
        String[] colors = {
                "\u001B[31m", // Red
                "\u001B[32m", // Green
                "\u001B[33m", // Yellow
                "\u001B[34m", // Blue
                "\u001B[35m", // Purple
                "\u001B[36m"  // Cyan
        };

        // Print the ASCII art instantly
        System.err.println(creepyMessage);

        // Print the Spanish lines one by one
        for (String line : spanishLines) {

            String color = colors[random.nextInt(colors.length)];

            for (char c : line.toCharArray()) {
                System.err.print(color + c + "\u001B[0m");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.err.println();
        }

        // Wait for user input to continue
        System.out.println("\u001B[31mPress Enter to continue...\u001B[0m");
        new Scanner(System.in).nextLine();

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

    @Override
    public void delete(TicketDTO dto) {

        String deleteItemsSql = "DELETE FROM TicketProducts WHERE ticketId = ?";
        String deleteTicketSql = "DELETE FROM tickets WHERE id = ?";

        try (Connection connection = H2DatabaseConnection.getConnection();
             PreparedStatement deleteItemsStmt = connection.prepareStatement(deleteItemsSql);
             PreparedStatement deleteTicketStmt = connection.prepareStatement(deleteTicketSql)) {

            connection.setAutoCommit(false);

            deleteItemsStmt.setString(1, dto.id());
            deleteItemsStmt.executeUpdate();

            deleteTicketStmt.setString(1, dto.id());
            deleteTicketStmt.executeUpdate();

            connection.commit();
            logger.info("Transaction committed successfully for deleting Ticket: {}", dto.id());

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while deleting Ticket: {}", dto.id(), e);
            rollback();
        }
    }

    @Override
    public TicketDTO get(String id) {
        String SELECT_TICKET_SQL = "SELECT * FROM tickets WHERE id = ?";
        String SELECT_ITEMS_SQL = "SELECT * FROM TicketProducts tp JOIN products p ON tp.productId = p.id WHERE tp.ticketId = ?";

        try (Connection connection = H2DatabaseConnection.getConnection();
             PreparedStatement ticketStmt = connection.prepareStatement(SELECT_TICKET_SQL);
             PreparedStatement itemsStmt = connection.prepareStatement(SELECT_ITEMS_SQL)) {

            ticketStmt.setString(1, id);
            try (ResultSet ticketRs = ticketStmt.executeQuery()) {
                if (ticketRs.next()) {

                    String ticketId = ticketRs.getString("id");
                    LocalDateTime dateTime = ticketRs.getTimestamp("dateTime").toLocalDateTime();
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

    @Override
    public Collection<TicketDTO> getAll() {
        String SELECT_TICKETS_SQL = "SELECT * FROM tickets";
        String SELECT_ITEMS_SQL = "SELECT * FROM TicketProducts tp JOIN products p ON tp.productId = p.id WHERE tp.ticketId = ?";
        List<TicketDTO> tickets = new ArrayList<>();

        try (Connection connection = H2DatabaseConnection.getConnection();
             PreparedStatement ticketsStmt = connection.prepareStatement(SELECT_TICKETS_SQL);
             PreparedStatement itemsStmt = connection.prepareStatement(SELECT_ITEMS_SQL);
             ResultSet ticketsRs = ticketsStmt.executeQuery()) {

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
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching all tickets", e);
        }
        return tickets;
    }
}
