package com.itacademy.sigma_team.print_stock.use_cases;

import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Tree;
import com.itacademy.sigma_team.flower_shop.FlowerShop;

import java.util.Scanner;

public final class PrintStockUseCase {

    public void exec(FlowerShop flowerShop) {

        final Scanner scanner = new Scanner(System.in);
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_CYAN = "\u001B[36m";

        System.out.println(ANSI_BLUE + "Welcome to " + flowerShop.name + "!" + ANSI_RESET + "\n");
        System.out.println(ANSI_CYAN + "Here is our current stock:" + ANSI_RESET + "\n");

        System.out.println(ANSI_GREEN + "Flowers:" + ANSI_RESET);
        for (Flower flower : flowerShop.flowers) {
            String stockColor = flower.getStock() > 0 ? ANSI_PURPLE : ANSI_RED;
            System.out.println(" - " + ANSI_PURPLE + "Name: " + ANSI_RESET + flower.getName() + ", " +
                    ANSI_PURPLE + "Color: " + ANSI_RESET + flower.getColor() + ", " +
                    ANSI_PURPLE + "Price: " + ANSI_RESET + "€" + flower.calculatePrice() + ", " +
                    stockColor + "Stock: " + ANSI_RESET + flower.getStock());
        }
        System.out.println();

        System.out.println(ANSI_GREEN + "Trees:" + ANSI_RESET);
        for (Tree tree : flowerShop.trees) {
            String stockColor = tree.getStock() > 0 ? ANSI_YELLOW : ANSI_RED;
            System.out.println(" - " + ANSI_YELLOW + "Name: " + ANSI_RESET + tree.getName() + ", " +
                    ANSI_YELLOW + "Height: " + ANSI_RESET + tree.getHeight() + " meters, " +
                    ANSI_YELLOW + "Price: " + ANSI_RESET + "€" + tree.calculatePrice() + ", " +
                    stockColor + "Stock: " + ANSI_RESET + tree.getStock());
        }
        System.out.println();

        System.out.println(ANSI_GREEN + "Decorations:" + ANSI_RESET);
        for (Decoration decoration : flowerShop.decorations) {
            String stockColor = decoration.getStock() > 0 ? ANSI_CYAN : ANSI_RED;
            System.out.println(" - " + ANSI_CYAN + "Name: " + ANSI_RESET + decoration.getName() + ", " +
                    ANSI_CYAN + "Material: " + ANSI_RESET + decoration.getMaterial() + ", " +
                    ANSI_CYAN + "Price: " + ANSI_RESET + "€" + decoration.calculatePrice() + ", " +
                    stockColor + "Stock: " + ANSI_RESET + decoration.getStock());
        }
        System.out.println();

        System.out.print(ANSI_YELLOW + "Press any key to continue..." + ANSI_RESET);
        scanner.nextLine(); // Wait for user input



    }

}
