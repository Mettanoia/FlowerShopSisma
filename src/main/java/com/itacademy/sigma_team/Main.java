package com.itacademy.sigma_team;

import com.itacademy.sigma_team.flowers.repositories.DatabaseSeeder;
import com.itacademy.sigma_team.flowers.repositories.TableCreator;

public class Main {


    public static void main(String[] args) {
        TableCreator.createDatabaseAndTables();
        //DatabaseSeeder.seedDatabase();

    }
}