package com.itacademy.sigma_team.ui.menus;

import com.itacademy.sigma_team.common.Adder;
import com.itacademy.sigma_team.domain.Tree;
import com.itacademy.sigma_team.ui.CrudControllerAdapter;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TreeMenu {

    private final Supplier<Collection<Tree>> getAllTreesUseCase;
    private final Adder<Tree> addTreeUseCase;
    private final Consumer<Tree> deleteTreeUseCase;
    private final MenuUtils menuUtils;

    public TreeMenu(CrudControllerAdapter<Tree, String> treeController) {
        this.getAllTreesUseCase = treeController;
        this.deleteTreeUseCase = treeController;
        this.addTreeUseCase = treeController;
        this.menuUtils = MenuUtils.getInstance();
    }

    public void showMenu() {

        AtomicInteger selectedIndex = new AtomicInteger(0);
        Scanner scanner = new Scanner(System.in);

        while (true) {

            List<Tree> trees = List.copyOf(getAllTreesUseCase.get());
            menuUtils.clearScreen();
            menuUtils.printHeader("Tree Menu");
            printTable(trees, selectedIndex.get());
            menuUtils.printFooter();

            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "w":
                    if (selectedIndex.get() > 0) {
                        selectedIndex.decrementAndGet();
                    }
                    break;
                case "s":
                    if (selectedIndex.get() < trees.size() - 1) {
                        selectedIndex.incrementAndGet();
                    }
                    break;
                case "enter":
                    menuUtils.clearScreen();
                    System.out.println(colorText("You selected: " + formatTree(trees.get(selectedIndex.get())), "\033[0;34m"));
                    return;
                case "a":
                    menuUtils.clearScreen();
                    addNewTree(scanner, trees);
                    break;
                case "d":
                    menuUtils.clearScreen();
                    deleteSelectedTree(trees, selectedIndex.get());
                    if (selectedIndex.get() >= trees.size() && selectedIndex.get() > 0) {
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
                        if (index >= 0 && index < trees.size()) {
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

    private void addNewTree(Scanner scanner, List<Tree> trees) {
        try {

            System.out.println("Enter tree name:");
            String name = scanner.nextLine();

            System.out.println("Enter tree height:");
            double height = Double.parseDouble(scanner.nextLine());

            System.out.println("Enter tree price:");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.println("Enter tree stock:");
            int stock = Integer.parseInt(scanner.nextLine());

            addTreeUseCase.add(new Tree(name, height, price, stock));
            System.out.println(colorText("Tree added successfully.", "\033[0;32m"));

        } catch (NumberFormatException e) {

            System.out.println(colorText("Invalid input for height, price, or stock. Please try again.", "\033[0;31m"));
            addNewTree(scanner, trees);

        }

    }

    private void deleteSelectedTree(List<Tree> trees, int selectedIndex) {

        if (trees.isEmpty()) {
            System.out.println(colorText("No trees to delete.", "\033[0;31m")); // Red for error message
        } else {
            deleteTreeUseCase.accept(trees.get(selectedIndex));
            System.out.println(colorText("Tree deleted successfully.", "\033[0;32m")); // Green for success message
        }

    }

    private void printTable(List<Tree> trees, int selectedIndex) {

        System.out.println(String.format("%-4s %-20s %-15s %-10s %-10s", "Idx", "Name", "Height", "Price", "Stock"));
        System.out.println("---------------------------------------------------------------------");

        for (int i = 0; i < trees.size(); i++) {
            Tree tree = trees.get(i);
            String line = String.format("%-4d %-20s %-15s %-10.2f %-10d", i, tree.getName(), tree.getHeight(), tree.getPrice(), tree.getStock());
            if (i == selectedIndex) {
                System.out.println(colorText(">> " + line, "\033[0;32m")); // Green for selected
            } else {
                System.out.println("   " + line);
            }
        }

    }

    private String formatTree(Tree tree) {

        return String.format("%s (Height: %.2f) - $%.2f [%d in stock]",
                colorText(tree.getName(), "\033[1;34m"), // Blue for name
                tree.getHeight(),
                tree.getPrice(),
                tree.getStock());

    }

    private String colorText(String text, String colorCode) {
        return colorCode + text + "\033[0m";
    }

    private static void unsupportedOperation() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
