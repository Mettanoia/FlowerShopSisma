package com.itacademy.sigma_team;

import com.itacademy.sigma_team.cli_controller.CliController;
import com.itacademy.sigma_team.domain.*;
import com.itacademy.sigma_team.flower_shop.FlowerShop;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MainMenu {

    private final CliController cliController;
    private final Scanner scanner;

    public MainMenu() {
        this.cliController = new CliController();
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Create Flower Shop");
            System.out.println("2. Add Tree");
            System.out.println("3. Add Flower");
            System.out.println("4. Add Decoration");
            System.out.println("5. Show Stock");
            System.out.println("6. Remove Tree");
            System.out.println("7. Remove Flower");
            System.out.println("8. Remove Decoration");
            System.out.println("9. Print Stock with Quantities");
            System.out.println("10. Print Total Value of Flower Shop");
            System.out.println("11. Create Purchase Ticket");
            System.out.println("12. Show Purchase History");
            System.out.println("13. View Total Money Earned");
            System.out.println("14. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
               // case 1 -> createFlowerShop();
                case 2 -> addTree();
                case 3 -> addFlower();
                case 4 -> addDecoration();
                case 5 -> cliController.printStock();
                case 6 -> deleteTree();
                case 7 -> deleteFlower();
                case 8 -> deleteDecoration();
                case 9 -> cliController.printPurchaseHistory();
                case 10 -> cliController.printBenefits();
                case 11 -> createTicket();
                case 12 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /*
    private void createFlowerShop() {
        System.out.print("Enter the name of the flower shop: ");
        String name = scanner.nextLine();
        cliController.createFlowerShop(new FlowerShop(name));
        System.out.println("Flower shop created successfully.");
    }
*/
    private void addTree() {
        System.out.print("Enter the species of the tree: ");
        String name = scanner.nextLine();
        System.out.print("Enter the height of the tree: ");
        double height = scanner.nextDouble();
        System.out.print("Enter the price of the tree: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter the steco of the tree: ");
        int stock =scanner.nextInt();
        cliController.addTree(new Tree(name, height, price, stock)); // Placeholder for stock
        System.out.println("Tree added successfully.");
    }

    private void addFlower() {
        System.out.print("Enter the name of the flower: ");
        String name = scanner.nextLine();
        System.out.print("Enter the color of the flower: ");
        String color = scanner.nextLine();
        System.out.print("Enter the price of the flower: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        cliController.addFlower(new Flower(name, color, price, 10)); // Placeholder for stock
        System.out.println("Flower added successfully.");
    }

    private void addDecoration() {
        System.out.print("Enter the name of the decoration: ");
        String name = scanner.nextLine();
        System.out.print("Enter the material of the decoration (WOOD/PLASTIC): ");
        String materialStr = scanner.nextLine();
        Material material = Material.valueOf(materialStr.toUpperCase());
        System.out.print("Enter the price of the decoration: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        cliController.addDecoration(new Decoration(name, material, price, 10)); // Placeholder for stock
        System.out.println("Decoration added successfully.");
    }

    private void deleteTree() {
        List<Tree> trees = cliController.listTrees();
        for (int i = 0; i < trees.size(); i++) {
            System.out.println(i + ". " + trees.get(i).getSpecies() + " - Height: " + trees.get(i).getHeight() + " - Price: " + trees.get(i).getPrice());
        }
        System.out.print("Select the index of the tree to remove: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (index >= 0 && index < trees.size()) {
            cliController.deleteTree(trees.get(index));
            System.out.println("Tree removed successfully.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    private void deleteFlower() {
        List<Flower> flowers = cliController.listFlowers();
        for (int i = 0; i < flowers.size(); i++) {
            System.out.println(i + ". " + flowers.get(i).getName() + " - Color: " + flowers.get(i).getColor() + " - Price: " + flowers.get(i).getPrice());
        }
        System.out.print("Select the index of the flower to remove: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (index >= 0 && index < flowers.size()) {
            cliController.deleteFlower(flowers.get(index));
            System.out.println("Flower removed successfully.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    private void deleteDecoration() {
        List<Decoration> decorations = cliController.listDecorations();
        for (int i = 0; i < decorations.size(); i++) {
            System.out.println(i + ". " + decorations.get(i).getName() + " - Material: " + decorations.get(i).getMaterial() + " - Price: " + decorations.get(i).getPrice());
        }
        System.out.print("Select the index of the decoration to remove: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (index >= 0 && index < decorations.size()) {
            cliController.deleteDecoration(decorations.get(index));
            System.out.println("Decoration removed successfully.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    private void createTicket() {
        String ticketId = UUID.randomUUID().toString();
        List<TicketItem> items = new ArrayList<>();

        while (true) {
            System.out.println("Add an item to the ticket:");
            System.out.println("1. Add Tree");
            System.out.println("2. Add Flower");
            System.out.println("3. Add Decoration");
            System.out.println("4. Finish and Create Ticket");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1 -> addTreeToTicket(items, ticketId);
                case 2 -> addFlowerToTicket(items, ticketId);
                case 3 -> addDecorationToTicket(items, ticketId);
                case 4 -> {
                    cliController.addTicket(items);
                    System.out.println("Ticket created successfully.");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addTreeToTicket(List<TicketItem> items, String ticketId) {
        List<Tree> trees = cliController.listTrees();
        for (int i = 0; i < trees.size(); i++) {
            System.out.println(i + ". " + trees.get(i).getSpecies() + " - Height: " + trees.get(i).getHeight() + " - Price: " + trees.get(i).getPrice());
        }
        System.out.print("Select the index of the tree to add: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (index >= 0 && index < trees.size()) {
            System.out.print("Enter the quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            Tree tree = trees.get(index);
            items.add(new TicketItem(ticketId, tree.getId(), "tree", quantity, tree.getPrice()));
            System.out.println("Tree added to ticket.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    private void addFlowerToTicket(List<TicketItem> items, String ticketId) {
        List<Flower> flowers = cliController.listFlowers();
        for (int i = 0; i < flowers.size(); i++) {
            System.out.println(i + ". " + flowers.get(i).getName() + " - Color: " + flowers.get(i).getColor() + " - Price: " + flowers.get(i).getPrice());
        }
        System.out.print("Select the index of the flower to add: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (index >= 0 && index < flowers.size()) {
            System.out.print("Enter the quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            Flower flower = flowers.get(index);
            items.add(new TicketItem(ticketId, flower.getId(), "flower", quantity, flower.getPrice()));
            System.out.println("Flower added to ticket.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    private void addDecorationToTicket(List<TicketItem> items, String ticketId) {
        List<Decoration> decorations = cliController.listDecorations();
        for (int i = 0; i < decorations.size(); i++) {
            System.out.println(i + ". " + decorations.get(i).getName() + " - Material: " + decorations.get(i).getMaterial() + " - Price: " + decorations.get(i).getPrice());
        }
        System.out.print("Select the index of the decoration to add: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (index >= 0 && index < decorations.size()) {
            System.out.print("Enter the quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            Decoration decoration = decorations.get(index);
            items.add(new TicketItem(ticketId, decoration.getId(), "decoration", quantity, decoration.getPrice()));
            System.out.println("Decoration added to ticket.");
        } else {
            System.out.println("Invalid index.");
        }
    }
}
