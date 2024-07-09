package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.dtos.TicketItem;
import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.flowers.repositories.SqlDatabaseConnection;
import com.itacademy.sigma_team.tickets.use_cases.TicketGateway;
import com.itacademy.sigma_team.dtos.TreeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class TicketSqlRepository implements TicketGateway {

    private static final Logger logger = LoggerFactory.getLogger(TicketSqlRepository.class);

    @Override
    public void add(TicketDTO dto) {

        try (Connection connection = SqlDatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            // Disable auto-commit mode
            connection.setAutoCommit(false);

            // Insert into TicketDTO table
            String insertTicketDto = "INSERT INTO TicketDTO (id) VALUES ('" + dto.id() + "');";
            statement.executeUpdate(insertTicketDto);

            for (TicketItem ticketItem : dto.ticketItems()) {

                String insertStatement = getInsertStatement(ticketItem);

                statement.executeUpdate(insertStatement);

            }

            connection.commit();

        } catch (SQLException e) {

            logger.error("SQL Exception occurred while adding TicketDTO: {}", dto.id(), e);
            rollback();

        }

    }

    private String getInsertStatement(TicketItem ticketItem) {

        return switch (ticketItem) {

            case FlowerDTO flower -> "INSERT INTO FlowerDTO (id, name, color, price, stock) " +
                    "VALUES ('" + flower.id() + "', '" + flower.name() + "', '" + flower.color() + "', " +
                    flower.price() + ", " + flower.stock() + ");";

            case TreeDTO tree -> "INSERT INTO TreeDTO (id, species, height, price) " +
                    "VALUES ('" + tree.id() + "', '" + tree.species() + "', " + tree.height() + ", " + tree.price() + ");";

            case DecorationDTO decoration -> "INSERT INTO DecorationDTO (id, material) " +
                    "VALUES (" + decoration.id() + ", '" + decoration.material() + "');";

        };

    }

    private void rollback() {

        try (Connection connection = SqlDatabaseConnection.getConnection()) {
            if (connection != null) {

                connection.rollback();
                logger.info("Transaction rolled back successfully.");

            }
        } catch (SQLException rollbackEx) {

            logger.error("Failed to rollback transaction", rollbackEx);
            throw new RuntimeException("Failed to rollback transaction", rollbackEx);

        }

    }

    @Override
    public void delete(TicketDTO dto) throws IOException {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    public TicketDTO get(String id) {
        return null;
    }

}
