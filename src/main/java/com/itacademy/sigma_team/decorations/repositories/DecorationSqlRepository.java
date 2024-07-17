package com.itacademy.sigma_team.cli_controller;

import com.itacademy.sigma_team.decorations.use_cases.*;
import com.itacademy.sigma_team.domain.*;
import com.itacademy.sigma_team.dtos.TicketItem;
import com.itacademy.sigma_team.flowers.use_cases.*;
import com.itacademy.sigma_team.print_stock.use_cases.PrintStockUseCase;
import com.itacademy.sigma_team.tickets.use_cases.*;
import com.itacademy.sigma_team.trees.use_cases.*;

import java.time.LocalDateTime;
import java.util.*;

public final class CliController {

    // Flowers use cases
    private final AddFlowerUseCase addFlowerUseCase;
    private final DeleteFlowerUseCase deleteFlowerUseCase;
    private final GetAllFlowersUseCase getAllFlowersUseCase;
    private final DecrementFlowerStockUseCase decrementFlowerStockUseCase;

    // Decoration use cases
    private final AddDecorationUseCase addDecorationUseCase;
    private final DeleteDecorationUseCase deleteDecorationUseCase;
    private final GetAllDecorationsUseCase getAllDecorationsUseCase;
    private final DecrementDecorationStockUseCase decrementDecorationStockUseCase;

    // Printing use cases
    private final PrintStockUseCase printStockUseCase;

    // Tree use cases
    private final AddTreeUseCase addTreeUseCase;
    private final DeleteTreeUseCase deleteTreeUseCase;
    private final GetAllTreesUseCase getAllTreesUseCase;
    private final DecrementTreeStockUseCase decrementTreeStockUseCase;

    // Ticket use cases
    private final AddTicketUseCase addTicketUseCase;
    private final DeleteTicketUseCase deleteTicketUseCase;
    private final GetTicketUseCase getTicketUseCase;
    private final GetAllTicketsUseCase getAllTicketsUseCase;
    private final PrintTotalMoneyEarnedUseCase printTotalMoneyEarnedUseCase;

    private final Scanner scanner;

    public CliController(AddFlowerUseCase addFlowerUseCase, DeleteFlowerUseCase deleteFlowerUseCase,
                         GetAllFlowersUseCase getAllFlowersUseCase, DecrementFlowerStockUseCase decrementFlowerStockUseCase,
                         AddDecorationUseCase addDecorationUseCase, DeleteDecorationUseCase deleteDecorationUseCase,
                         GetAllDecorationsUseCase getAllDecorationsUseCase, DecrementDecorationStockUseCase decrementDecorationStockUseCase,
                         PrintStockUseCase printStockUseCase, AddTreeUseCase addTreeUseCase, GetAllTreesUseCase getAllTreesUseCase,
                         DecrementTreeStockUseCase decrementTreeStockUseCase, AddTicketUseCase addTicketUseCase,
                         DeleteTicketUseCase deleteTicketUseCase, DeleteTreeUseCase deleteTreeUseCase, GetTicketUseCase getTicketUseCase,
                         GetAllTicketsUseCase getAllTicketsUseCase, PrintTotalMoneyEarnedUseCase printTotalMoneyEarnedUseCase) {
        this.addFlowerUseCase = addFlowerUseCase;
        this.deleteFlowerUseCase = deleteFlowerUseCase;
        this.getAllFlowersUseCase = getAllFlowersUseCase;
        this.decrementFlowerStockUseCase = decrementFlowerStockUseCase;
        this.addDecorationUseCase = addDecorationUseCase;
        this.deleteDecorationUseCase = deleteDecorationUseCase;
        this.getAllDecorationsUseCase = getAllDecorationsUseCase;
        this.decrementDecorationStockUseCase = decrementDecorationStockUseCase;
        this.printStockUseCase = printStockUseCase;
        this.addTreeUseCase = addTreeUseCase;
        this.getAllTreesUseCase = getAllTreesUseCase;
        this.decrementTreeStockUseCase = decrementTreeStockUseCase;
        this.addTicketUseCase = addTicketUseCase;
        this.deleteTicketUseCase = deleteTicketUseCase;
        this.deleteTreeUseCase = deleteTreeUseCase;
        this.getTicketUseCase = getTicketUseCase;
        this.getAllTicketsUseCase = getAllTicketsUseCase;
        this.printTotalMoneyEarnedUseCase = printTotalMoneyEarnedUseCase;
        this.scanner = new Scanner(System.in);
    }

    // Flower shop entrypoint
    private void createFlowerShop() {
        System.out.print("Enter the name of the flower shop: ");
        String name = scanner.nextLine();
        // Implementation of creating a flower shop
        System.out.println("Flower shop " + name + " created successfully.");
    }

    // Flowers entry points
    private void addFlower() {
        System.out.print("Enter flower name: ");
        String name = scanner.nextLine();
        System.out.print("Enter flower color: ");
        String color = scanner.nextLine();
        System.out.print("Enter flower price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter flower stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Flower flower = new Flower(name, color, price, stock);
        this.addFlowerUseCase.exec(flower);
        System.out.println("Flower added successfully.");
    }

    private void deleteFlower() {
        Collection<Flower> flowers = getAllFlowersUseCase.exec();
        if (flowers.isEmpty()) {
            System.out.println("No flowers available to delete.");
        } else {
            List<Flower> flowerList = new ArrayList<>(flowers);
            System.out.println("Select a flower to delete:");
            for (int i = 0; i < flowerList.size(); i++) {
                Flower flower = flowerList.get(i);
                System.out.printf("%d. %s (Color: %s, Price: %.2f, Stock: %d)\n", i + 1, flower.getName(), flower.getColor(), flower.getPrice(), flower.getStock());
            }

            System.out.print("Enter the number of the flower to delete: ");
            int index = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline

            if (index < 0 || index >= flowerList.size()) {
                System.out.println("Invalid selection. Please try again.");
            } else {
                Flower flowerToDelete = flowerList.get(index);
                this.deleteFlowerUseCase.exec(flowerToDelete);
                System.out.println("Flower deleted successfully.");
            }
        }
    }

    // Trees entry points
    private void addTree() {
        System.out.print("Enter tree species: ");
        String species = scanner.nextLine();
        System.out.print("Enter tree height: ");
        double height = scanner.nextDouble();
        System.out.print("Enter tree price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter tree stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Tree tree = new Tree(species, height, price, stock);
        this.addTreeUseCase.exec(tree);
        System.out.println("Tree added successfully.");
    }

    private void deleteTree() {
        Collection<Tree> trees = getAllTreesUseCase.exec();
        if (trees.isEmpty()) {
            System.out.println("No trees available to delete.");
        } else {
            List<Tree> treeList = new ArrayList<>(trees);
            System.out.println("Select a tree to delete:");
            for (int i = 0; i < treeList.size(); i++) {
                Tree tree = treeList.get(i);
                System.out.printf("%d. %s (Height: %.2f, Price: %.2f, Stock: %d)\n", i + 1, tree.getName(), tree.getHeight(), tree.getPrice(), tree.getStock());
            }

            System.out.print("Enter the number of the tree to delete: ");
            int index = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline

            if (index < 0 || index >= treeList.size()) {
                System.out.println("Invalid selection. Please try again.");
            } else {
                Tree treeToDelete = treeList.get(index);
                this.deleteTreeUseCase.exec(treeToDelete);
                System.out.println("Tree deleted successfully.");
            }
        }
    }

    // Decoration entry points
    private void addDecoration() {
        System.out.print("Enter decoration name: ");
        String name = scanner.nextLine();
        System.out.print("Enter decoration material (WOOD/PLASTIC): ");
        String material = scanner.nextLine().toUpperCase();
        System.out.print("Enter decoration price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter decoration stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Decoration decoration = new Decoration(name, Material.valueOf(material), price, stock);
        this.addDecorationUseCase.exec(decoration);
        System.out.println("Decoration added successfully.");
    }

    private void deleteDecoration() {
        Collection<Decoration> decorations = getAllDecorationsUseCase.exec();
        if (decorations.isEmpty()) {
            System.out.println("No decorations available to delete.");
        } else {
            List<Decoration> decorationList = new ArrayList<>(decorations);
            System.out.println("Select a decoration to delete:");
            for (int i = 0; i < decorationList.size(); i++) {
                Decoration decoration = decorationList.get(i);
                System.out.printf("%d. %s (Material: %s, Price: %.2f, Stock: %d)\n", i + 1, decoration.getName(), decoration.getMaterial(), decoration.getPrice(), decoration.getStock());
            }

            System.out.print("Enter the number of the decoration to delete: ");
            int index = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline

            if (index < 0 || index >= decorationList.size()) {
                System.out.println("Invalid selection. Please try again.");
            } else {
                Decoration decorationToDelete = decorationList.get(index);
                this.deleteDecorationUseCase.exec(decorationToDelete);
                System.out.println("Decoration deleted successfully.");
            }
        }
    }

    // Ticket entry points
    private void createTicket() {
        List<TicketItem> ticketItems = new ArrayList<>();
        String ticketId = UUID.randomUUID().toString();
        boolean addingItems = true;

        while (addingItems) {
            System.out.println("Select product type to add to the ticket:");
            System.out.println("1. Flower");
            System.out.println("2. Tree");
            System.out.println("3. Decoration");
            System.out.println("4. Finish and create ticket");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1 -> ticketItems.add(selectFlower());
                case 2 -> ticketItems.add(selectTree());
                case 3 -> ticketItems.add(selectDecoration());
                case 4 -> {
                    addingItems = false;
                    addTicketUseCase.exec(new Ticket(ticketId, LocalDateTime.now(), ticketItems,0,0));
                    System.out.println("Ticket created successfully.");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private Flower selectFlower() {
        Collection<Flower> flowers = getAllFlowersUseCase.exec();
        if (flowers.isEmpty()) {
            System.out.println("No flowers available.");
            return null;
        } else {
            List<Flower> flowerList = new ArrayList<>(flowers);
            System.out.println("Select a flower:");
            for (int i = 0; i < flowerList.size(); i++) {
                Flower flower = flowerList.get(i);
                System.out.printf("%d. %s (Color: %s, Price: %.2f, Stock: %d)\n", i + 1, flower.getName(), flower.getColor(), flower.getPrice(), flower.getStock());
            }

            System.out.print("Enter the number of the flower to select: ");
            int index = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline

            if (index < 0 || index >= flowerList.size()) {
                System.out.println("Invalid selection. Please try again.");
                return selectFlower();
            } else {
                Flower flower = flowerList.get(index);
                System.out.print("Enter the quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (quantity > flower.getStock()) {
                    System.out.println("Not enough stock. Please try again.");
                    return selectFlower();
                } else {
                    decrementFlowerStockUseCase.exec(flower.getId(), quantity);
                    return new Flower(flower.getId(), flower.getName(), flower.getColor(), flower.getPrice(), quantity);
                }
            }
        }
    }

    private Tree selectTree() {
        Collection<Tree> trees = getAllTreesUseCase.exec();
        if (trees.isEmpty()) {
            System.out.println("No trees available.");
            return null;
        } else {
            List<Tree> treeList = new ArrayList<>(trees);
            System.out.println("Select a tree:");
            for (int i = 0; i < treeList.size(); i++) {
                Tree tree = treeList.get(i);
                System.out.printf("%d. %s (Height: %.2f, Price: %.2f, Stock: %d)\n", i + 1, tree.getName(), tree.getHeight(), tree.getPrice(), tree.getStock());
            }

            System.out.print("Enter the number of the tree to select: ");
            int index = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline

            if (index < 0 || index >= treeList.size()) {
                System.out.println("Invalid selection. Please try again.");
                return selectTree();
            } else {
                Tree tree = treeList.get(index);
                System.out.print("Enter the quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (quantity > tree.getStock()) {
                    System.out.println("Not enough stock. Please try again.");
                    return selectTree();
                } else {
                    decrementTreeStockUseCase.exec(tree.getId(), quantity);
                    return new Tree(tree.getId(), tree.getName(), tree.getHeight(), tree.getPrice(), quantity);
                }
            }
        }
    }

    private Decoration selectDecoration() {
        Collection<Decoration> decorations = getAllDecorationsUseCase.exec();
        if (decorations.isEmpty()) {
            System.out.println("No decorations available.");
            return null;
        } else {
            List<Decoration> decorationList = new ArrayList<>(decorations);
            System.out.println("Select a decoration:");
            for (int i = 0; i < decorationList.size(); i++) {
                Decoration decoration = decorationList.get(i);
                System.out.printf("%d. %s (Material: %s, Price: %.2f, Stock: %d)\n", i + 1, decoration.getName(), decoration.getMaterial(), decoration.getPrice(), decoration.getStock());
            }

            System.out.print("Enter the number of the decoration to select: ");
            int index = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline

            if (index < 0 || index >= decorationList.size()) {
                System.out.println("Invalid selection. Please try again.");
                return selectDecoration();
            } else {
                Decoration decoration = decorationList.get(index);
                System.out.print("Enter the quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (quantity > decoration.getStock()) {
                    System.out.println("Not enough stock. Please try again.");
                    return selectDecoration();
                } else {
                    decrementDecorationStockUseCase.exec(decoration.getId(), quantity);
                    return new Decoration(decoration.getId(), decoration.getName(), decoration.getMaterial(), decoration.getPrice(), quantity);
                }
            }
        }
    }

    // Printing entry points
    public void printStock() { this.printStockUseCase.exec(); }
    private void printPurchaseHistory() {
        Collection<Ticket> tickets = getAllTicketsUseCase.exec();
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }
    private void printTotalMoneyEarned() {
        double totalMoneyEarned = printTotalMoneyEarnedUseCase.exec();
        System.out.printf("Total Money Earned: %.2f\n", totalMoneyEarned);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Create Flower Shop");
            System.out.println("2. Add Product (Tree, Flower, Decoration)");
            System.out.println("3. Show Stock");
            System.out.println("4. Remove Product (Tree, Flower, Decoration)");
            System.out.println("5. Print Stock with Quantities");
            System.out.println("6. Print Total Value of Flower Shop");
            System.out.println("7. Create Purchase Ticket");
            System.out.println("8. Show Purchase History");
            System.out.println("9. View Total Money Earned");
            System.out.println("10. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1 -> createFlowerShop();
                case 2 -> addProductMenu();
                case 3 -> removeProductMenu();
                case 4 -> printStockWithQuantities();
                case 5 -> printTotalValueOfFlowerShop();
                case 6 -> createTicket();
                case 7 -> printPurchaseHistory();
                case 8 -> printTotalMoneyEarned();
                case 9 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addProductMenu() {
        System.out.println("Add Product Menu:");
        System.out.println("1. Add Tree");
        System.out.println("2. Add Flower");
        System.out.println("3. Add Decoration");
        System.out.print("Select an option: ");

        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (option) {
            case 1 -> addTree();
            case 2 -> addFlower();
            case 3 -> addDecoration();
            default -> System.out.println("Invalid option. Please try again.");
        }
    }

    private void removeProductMenu() {
        System.out.println("Remove Product Menu:");
        System.out.println("1. Remove Tree");
        System.out.println("2. Remove Flower");
        System.out.println("3. Remove Decoration");
        System.out.print("Select an option: ");

        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (option) {
            case 1 -> deleteTree();
            case 2 -> deleteFlower();
            case 3 -> deleteDecoration();
            default -> System.out.println("Invalid option. Please try again.");
        }
    }

    private void printStockWithQuantities() {
        Collection<Flower> flowers = getAllFlowersUseCase.exec();
        Collection<Tree> trees = getAllTreesUseCase.exec();
        Collection<Decoration> decorations = getAllDecorationsUseCase.exec();

        System.out.println("Stock with Quantities:");

        System.out.println("Flowers:");
        for (Flower flower : flowers) {
            System.out.printf("Name: %s, Color: %s, Price: %.2f, Stock: %d\n", flower.getName(), flower.getColor(), flower.getPrice(), flower.getStock());
        }

        System.out.println("Trees:");
        for (Tree tree : trees) {
            System.out.printf("Name: %s, Height: %.2f, Price: %.2f, Stock: %d\n", tree.getName(), tree.getHeight(), tree.getPrice(), tree.getStock());
        }

        System.out.println("Decorations:");
        for (Decoration decoration : decorations) {
            System.out.printf("Name: %s, Material: %s, Price: %.2f, Stock: %d\n", decoration.getName(), decoration.getMaterial(), decoration.getPrice(), decoration.getStock());
        }
    }

    private void printTotalValueOfFlowerShop() {
        double totalValue = 0.0;

        Collection<Flower> flowers = getAllFlowersUseCase.exec();
        Collection<Tree> trees = getAllTreesUseCase.exec();
        Collection<Decoration> decorations = getAllDecorationsUseCase.exec();

        for (Flower flower : flowers) {
            totalValue += flower.getPrice() * flower.getStock();
        }

        for (Tree tree : trees) {
            totalValue += tree.getPrice() * tree.getStock();
        }

        for (Decoration decoration : decorations) {
            totalValue += decoration.getPrice() * decoration.getStock();
        }

        System.out.printf("Total Value of Flower Shop: %.2f\n", totalValue);
    }

}
