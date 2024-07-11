package com.itacademy.sigma_team;

import com.itacademy.sigma_team.decorations.repositories.DecorationSqlRepository;
import com.itacademy.sigma_team.domain.*;
import com.itacademy.sigma_team.flowers.FlowerMapper;
import com.itacademy.sigma_team.flowers.repositories.*;
import com.itacademy.sigma_team.services.TicketRepository;
import com.itacademy.sigma_team.services.TicketService;
import com.itacademy.sigma_team.tickets.repositories.TicketItemDTO;
import com.itacademy.sigma_team.tickets.repositories.TicketSqlRepository;
import com.itacademy.sigma_team.trees.repositories.TreeSqlRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void run() {
        // Crear la base de datos y las tablas en la base de datos SQL
        TableCreator.createDatabaseAndTables();

        // Insertar datos en la base de datos
        DatabaseSeeder.seedDatabase();

        // Probar la inserción de un ticket
        testInsertTicket();
    }

    public static void testInsertTicket() {
        // Crear los repositorios
        FlowerSqlRepository flowerRepository = new FlowerSqlRepository();
        TreeSqlRepository treeRepository = new TreeSqlRepository();
        DecorationSqlRepository decorationRepository = new DecorationSqlRepository();
        TicketSqlRepository ticketRepository = new TicketSqlRepository();

        // Crear el servicio de tickets
        TicketService ticketService = new TicketService(ticketRepository, flowerRepository, treeRepository, decorationRepository);

        // Generar un ID de ticket
        String ticketId = ticketService.generateTicketId();
        System.out.println("Generated Ticket ID: " + ticketId);

        // Crear ítems de ejemplo
        TicketItemDTO item1 = new TicketItemDTO("1", "flower", 3, 10.5, LocalDateTime.now());
        TicketItemDTO item2 = new TicketItemDTO("2", "tree", 1, 15.5, LocalDateTime.now());

        // Añadir ítems al ticket
        ticketService.addItemToTicket(ticketId, item1);
        ticketService.addItemToTicket(ticketId, item2);

        // Recuperar y mostrar el ticket
        try (Connection conn = SqlDatabaseConnection.getConnection()) {
            Ticket ticket = ticketService.getTicket(ticketId);
            System.out.println("Retrieved Ticket: \n" + ticket);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        run();
    }



}