package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.domain.Material;
import com.itacademy.sigma_team.dtos.*;
import com.itacademy.sigma_team.tickets.use_cases.TicketGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class TicketSqlRepository implements TicketGateway {

    private static final Logger logger = LoggerFactory.getLogger(TicketSqlRepository.class);

    @Override
    public void add(TicketDTO dto) {

        String insertTicketSql = "INSERT INTO tickets (id, dateTime) VALUES (?, ?)";
        String insertItemSql = "INSERT INTO products (id, name, color, height, material, price, stock, ticketId, type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb");

             PreparedStatement insertTicketStmt = connection.prepareStatement(insertTicketSql)) {

            // Start transaction
            connection.setAutoCommit(false);

            // Insert TicketDTO
            insertTicketStmt.setString(1, dto.getId());
            insertTicketStmt.setObject(2, dto.getDateTime());
            insertTicketStmt.executeUpdate();

            // Insert all the TicketItems
            for (TicketItem item : dto.getItems()) {
                insertItem(insertItemSql, item, dto.getId(), connection);
            }

            // Commit the transaction
            connection.commit();
            logger.info("Transaction committed successfully for Ticket: {}", dto.getId());

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while adding Ticket: {}", dto.getId(), e);
            rollback();
        }

    }

    /**
     * This method inserts an item into the products table.
     * It uses the Single Table Inheritance pattern with a discriminator column named 'type' to distinguish between different item types.
     * For more information on this pattern, see:
     * <a href="https://martinfowler.com/eaaCatalog/singleTableInheritance.html">Single Table Inheritance Pattern</a>
     */
    private void insertItem(String insertItemSql, TicketItem item, String ticketId, Connection connection) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(insertItemSql)) {

            commonAttributes(item, ticketId, stmt);

            // Item-specific attributes
            switch (item) {

                case FlowerDTO flower -> {

                    // Flower-only attributes
                    stmt.setString(3, flower.color());

                    // Non-Flower attributes set to null
                    stmt.setNull(4, Types.DOUBLE);  // height
                    stmt.setNull(5, Types.VARCHAR); // material

                    // Discriminator
                    stmt.setString(9, "Flower");
                }
                case TreeDTO tree -> {

                    // Tree-only attributes
                    stmt.setDouble(4, tree.height());

                    // Non-Tree attributes set to null
                    stmt.setNull(3, Types.VARCHAR); // color
                    stmt.setNull(5, Types.VARCHAR); // material

                    // Discriminator
                    stmt.setString(9, "Tree");
                }
                case DecorationDTO decoration -> {

                    // Decoration-only attributes
                    stmt.setString(5, String.valueOf(decoration.material()));

                    // Non-Decoration attributes set to null
                    stmt.setNull(3, Types.VARCHAR); // color
                    stmt.setNull(4, Types.DOUBLE);  // height

                    // Discriminator
                    stmt.setString(9, "Decoration");
                }

            }

            stmt.executeUpdate();
        }
    }


    private static void commonAttributes(TicketItem item, String ticketId, PreparedStatement stmt) throws SQLException {
        try {

            Class<?> clazz = item.getClass();

            // Get the methods
            Method getId = clazz.getMethod("id");
            Method getName = clazz.getMethod("name");
            Method getPrice = clazz.getMethod("price");
            Method getStock = clazz.getMethod("stock");

            // Use them
            String id = (String) getId.invoke(item);
            String name = (String) getName.invoke(item);
            double price = (double) getPrice.invoke(item);
            int stock = (int) getStock.invoke(item);

            // Set the common attributes in the prepared statement
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setDouble(6, price);
            stmt.setInt(7, stock);
            stmt.setString(8, ticketId);

        } catch (ReflectiveOperationException e) {
            logger.error("Unsupported TicketItem type");
        }

    }

    private void rollback() {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb")) {
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

        String deleteItemsSql = "DELETE FROM products WHERE ticketId = ?";
        String deleteTicketSql = "DELETE FROM tickets WHERE id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb");
             PreparedStatement deleteItemsStmt = connection.prepareStatement(deleteItemsSql);
             PreparedStatement deleteTicketStmt = connection.prepareStatement(deleteTicketSql)) {

            connection.setAutoCommit(false);

            deleteItemsStmt.setString(1, dto.getId());
            deleteItemsStmt.executeUpdate();

            deleteTicketStmt.setString(1, dto.getId());
            deleteTicketStmt.executeUpdate();

            connection.commit();
            logger.info("Transaction committed successfully for deleting Ticket: {}", dto.getId());

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while deleting Ticket: {}", dto.getId(), e);
            rollback();
        }
    }

    @Override
    public TicketDTO get(String id) {

        String SELECT_TICKET_SQL = "SELECT * FROM tickets WHERE id = ?";
        String SELECT_ITEMS_SQL = "SELECT * FROM products WHERE ticketId = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb");
             PreparedStatement ticketStmt = connection.prepareStatement(SELECT_TICKET_SQL);
             PreparedStatement itemsStmt = connection.prepareStatement(SELECT_ITEMS_SQL)) {

            ticketStmt.setString(1, id);
            try (ResultSet ticketRs = ticketStmt.executeQuery()) {
                if (ticketRs.next()) {

                    String ticketId = ticketRs.getString("id");
                    LocalDateTime dateTime = ticketRs.getTimestamp("dateTime").toLocalDateTime();
                    ArrayList<TicketItem> items = new ArrayList<>();

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

                            items.add(itemDTO);
                        }
                    }

                    return new TicketDTO(ticketId, dateTime, items);

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
        String SELECT_ITEMS_SQL = "SELECT * FROM products WHERE ticketId = ?";
        List<TicketDTO> tickets = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb");
             PreparedStatement ticketsStmt = connection.prepareStatement(SELECT_TICKETS_SQL);
             PreparedStatement itemsStmt = connection.prepareStatement(SELECT_ITEMS_SQL);
             ResultSet ticketsRs = ticketsStmt.executeQuery()) {

            while (ticketsRs.next()) {

                String ticketId = ticketsRs.getString("id");
                LocalDateTime dateTime = ticketsRs.getTimestamp("dateTime").toLocalDateTime();
                List<TicketItem> items = new ArrayList<>();

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

                        items.add(itemDTO);
                    }
                }

                TicketDTO ticketDTO = new TicketDTO(ticketId, dateTime, items);
                tickets.add(ticketDTO);
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching all tickets", e);
        }
        return tickets;
    }

}
