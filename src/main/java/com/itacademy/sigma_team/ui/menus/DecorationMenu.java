package com.itacademy.sigma_team.ui.menus;

import com.itacademy.sigma_team.common.Adder;
import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Material;
import com.itacademy.sigma_team.ui.CrudControllerAdapter;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DecorationMenu {

    private final Supplier<Collection<Decoration>> getAllDecorationsUseCase;
    private final Adder<Decoration> addDecorationUseCase;
    private final Consumer<Decoration> deleteDecorationUseCase;
    private final MenuUtils menuUtils;

    public DecorationMenu(CrudControllerAdapter<Decoration, String> decorationController) {
        this.getAllDecorationsUseCase = decorationController;
        this.deleteDecorationUseCase = decorationController;
        this.addDecorationUseCase = decorationController;
        this.menuUtils = MenuUtils.getInstance();
    }

    public void showMenu() {

        AtomicInteger selectedIndex = new AtomicInteger(0);
        Scanner scanner = new Scanner(System.in);

        while (true) {

            List<Decoration> decorations = List.copyOf(getAllDecorationsUseCase.get());
            menuUtils.clearScreen();
            menuUtils.printHeader("Decoration Menu");
            printTable(decorations, selectedIndex.get());
            menuUtils.printFooter();

            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "w":
                    if (selectedIndex.get() > 0) {
                        selectedIndex.decrementAndGet();
                    }
                    break;
                case "s":
                    if (selectedIndex.get() < decorations.size() - 1) {
                        selectedIndex.incrementAndGet();
                    }
                    break;
                case "enter":
                    menuUtils.clearScreen();
                    System.out.println(colorText("You selected: " + formatDecoration(decorations.get(selectedIndex.get())), "\033[0;34m"));
                    return;
                case "a":
                    menuUtils.clearScreen();
                    addNewDecoration(scanner, decorations);
                    break;
                case "d":
                    menuUtils.clearScreen();
                    deleteSelectedDecoration(decorations, selectedIndex.get());
                    if (selectedIndex.get() >= decorations.size() && selectedIndex.get() > 0) {
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
                        if (index >= 0 && index < decorations.size()) {
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

    private void addNewDecoration(Scanner scanner, List<Decoration> decorations) {
        try {
            System.out.println("Enter decoration name:");
            String name = scanner.nextLine();

            System.out.println("Enter decoration material: Choose - WOOD or PLASTIC ");
            String material = scanner.nextLine();

            System.out.println("Enter decoration price:");
            double price = Double.parseDouble(scanner.nextLine());

            int stock;
            while (true) {
                System.out.print("Enter decoration stock: ");
                stock = Integer.parseInt(scanner.nextLine());
                if (stock == 0) {
                    System.out.println(colorText("Stock cannot be zero. Please enter a valid stock amount","\033[0m"));
                } else {
                    break;
                }
            }


            addDecorationUseCase.add(new Decoration(name, Material.valueOf(material), price, stock));
            System.out.println(colorText("Decoration added successfully.", "\033[0;32m"));
        } catch (NumberFormatException e) {
            System.out.println(colorText("Invalid input for price or stock. Please try again.", "\033[0;31m"));
            addNewDecoration(scanner, decorations);
        }
    }

    private void deleteSelectedDecoration(List<Decoration> decorations, int selectedIndex) {
        if (decorations.isEmpty()) {
            System.out.println(colorText("No decorations to delete.", "\033[0;31m")); // Red for error message
        } else {
            deleteDecorationUseCase.accept(decorations.get(selectedIndex));
            System.out.println(colorText("Decoration deleted successfully.", "\033[0;32m")); // Green for success message
        }
    }

    private void printTable(List<Decoration> decorations, int selectedIndex) {
        System.out.println(String.format("%-4s %-20s %-15s %-10s %-10s", "Idx", "Name", "Material", "Price", "Stock"));
        System.out.println("---------------------------------------------------------------------");
        for (int i = 0; i < decorations.size(); i++) {
            Decoration decoration = decorations.get(i);
            String line = String.format("%-4d %-20s %-15s %-10.2f %-10d", i, decoration.getName(), decoration.getMaterial(), decoration.getPrice(), decoration.getStock());
            if (i == selectedIndex) {
                System.out.println(colorText(">> " + line, "\033[0;32m")); // Green for selected
            } else {
                System.out.println("   " + line);
            }
        }
    }

    private String formatDecoration(Decoration decoration) {
        return String.format("%s (%s) - $%.2f [%d in stock]",
                colorText(decoration.getName(), "\033[1;34m"), // Blue for name
                colorText(decoration.getMaterial().toString(), "\033[1;35m"), // Magenta for material
                decoration.getPrice(),
                decoration.getStock());
    }

    private String colorText(String text, String colorCode) {
        return colorCode + text + "\033[0m";
    }

    private static void unsupportedOperation() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
