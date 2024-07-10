package com.itacademy.sigma_team;

import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Ticket;
import com.itacademy.sigma_team.flowers.FlowerMapper;
import com.itacademy.sigma_team.flowers.repositories.DatabaseSeeder;
import com.itacademy.sigma_team.flowers.repositories.FlowerDTO;
import com.itacademy.sigma_team.flowers.repositories.FlowerSqlRepository;
import com.itacademy.sigma_team.flowers.repositories.TableCreator;
import com.itacademy.sigma_team.services.TicketService;
import com.itacademy.sigma_team.tickets.repositories.TicketItemDTO;
import com.itacademy.sigma_team.tickets.repositories.TicketSqlRepository;

import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        TableCreator.createDatabaseAndTables();
        DatabaseSeeder.seedDatabase();

    }
}