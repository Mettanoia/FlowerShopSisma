package com.itacademy.sigma_team.ui.menus;

import com.itacademy.sigma_team.common.Adder;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.ui.CrudControllerAdapter;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FlowerMenu {

    private final Supplier<Collection<Flower>> getAllFlowersUseCase;
    private final Adder<Flower> addFlowerUseCase;
    private final Consumer<Flower> deleteFlowerUseCase;
    private final MenuUtils menuUtils;

    public FlowerMenu(CrudControllerAdapter<Flower, String> flowerController) {
        this.getAllFlowersUseCase = flowerController;
        this.deleteFlowerUseCase = flowerController;
        this.addFlowerUseCase = flowerController;
        this.menuUtils = MenuUtils.getInstance();
    }

    public void showMenu() {


        AtomicInteger selectedIndex = new AtomicInteger(0);
        Scanner scanner = new Scanner(System.in);

        while (true) {

            List<Flower> flowers = List.copyOf(getAllFlowersUseCase.get());
            menuUtils.clearScreen();
            menuUtils.printHeader("Flower Menu");
            printTable(flowers, selectedIndex.get());
            menuUtils.printFooter();

            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "w":
                    if (selectedIndex.get() > 0) {
                        selectedIndex.decrementAndGet();
                    }
                    break;
                case "s":
                    if (selectedIndex.get() < flowers.size() - 1) {
                        selectedIndex.incrementAndGet();
                    }
                    break;
                case "enter":
                    menuUtils.clearScreen();
                    System.out.println(colorText("You selected: " + formatFlower(flowers.get(selectedIndex.get())), "\033[0;34m"));
                    return;
                case "a":
                    menuUtils.clearScreen();
                    addNewFlower(scanner, flowers);
                    break;
                case "d":
                    menuUtils.clearScreen();
                    deleteSelectedFlower(flowers, selectedIndex.get());
                    if (selectedIndex.get() >= flowers.size() && selectedIndex.get() > 0) {
                        selectedIndex.decrementAndGet();
                    }
                    break;
                case "q":
                    menuUtils.clearScreen();
                    System.out.println(colorText("Menu closed.", "\033[0;31m"));
                    return;
                default:
                    try {
                        int index = Integer.parseInt(input);
                        if (index >= 0 && index < flowers.size()) {
                            selectedIndex.set(index);
                        } else {
                            System.out.println(colorText("Invalid index. Please try again.", "\033[0;31m"));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(colorText("Invalid input. Please use 'w', 's', 'enter', 'a', 'd', 'q' or an index.", "\033[0;31m"));
                    }
                    break;
            }
        }
    }

    private void addNewFlower(Scanner scanner, List<Flower> flowers) {
        try {

            System.out.println("Enter flower name:");
            String name = scanner.nextLine();

            System.out.println("Enter flower color:");
            String color = scanner.nextLine();

            System.out.println("Enter flower price:");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.println("Enter flower stock:");
            int stock = Integer.parseInt(scanner.nextLine());

            addFlowerUseCase.add(new Flower(name, color, price, stock));
            System.out.println(colorText("Flower added successfully.", "\033[0;32m"));

        } catch (NumberFormatException e) {
            System.out.println(colorText("Invalid input for price or stock. Please try again.", "\033[0;31m"));
            addNewFlower(scanner, flowers);
        }
    }

    private void deleteSelectedFlower(List<Flower> flowers, int selectedIndex) {

        if (flowers.isEmpty()) {
            System.out.println(colorText("No flowers to delete.", "\033[0;31m"));
        } else {

            deleteFlowerUseCase.accept(flowers.get(selectedIndex));
            System.out.println(colorText("Flower deleted successfully.", "\033[0;32m"));

        }
    }

    private void printTable(List<Flower> flowers, int selectedIndex) {

        System.out.printf("%-4s %-20s %-15s %-10s %-10s%n", "Idx", "Name", "Color", "Price", "Stock");
        System.out.println("---------------------------------------------------------------------");

        for (int i = 0; i < flowers.size(); i++) {
            Flower flower = flowers.get(i);
            String line = String.format("%-4d %-20s %-15s %-10.2f %-10d", i, flower.getName(), flower.getColor(), flower.getPrice(), flower.getStock());
            if (i == selectedIndex) {
                System.out.println(colorText(">> " + line, "\033[0;32m")); // Green for selected
            } else {
                System.out.println("   " + line);
            }
        }

    }

    private String formatFlower(Flower flower) {

        return String.format("%s (%s) - $%.2f [%d in stock]",
                colorText(flower.getName(), "\033[1;34m"), // Blue for name
                colorText(flower.getColor(), "\033[1;35m"), // Magenta for color
                flower.getPrice(),
                flower.getStock());

    }

    private String colorText(String text, String colorCode) {
        return colorCode + text + "\033[0m";
    }

    private static void unsupportedOperation() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
