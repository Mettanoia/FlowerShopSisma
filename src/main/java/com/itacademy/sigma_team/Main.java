package com.itacademy.sigma_team;

import com.itacademy.sigma_team.testing_chuchus.DatabaseInitializer;

public class Main {

    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        App.run();
    }

}