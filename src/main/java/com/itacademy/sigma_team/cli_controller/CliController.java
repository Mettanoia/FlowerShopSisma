package com.itacademy.sigma_team.cli_controller;

import com.itacademy.sigma_team.decorations.use_cases.AddDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.DeleteDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.GetAllDecorationsUseCase;
import com.itacademy.sigma_team.domain.*;
import com.itacademy.sigma_team.flowers.use_cases.AddFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.DeleteFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.GetAllFlowersUseCase;
import com.itacademy.sigma_team.print_stock.use_cases.PrintStockUseCase;
import com.itacademy.sigma_team.tickets.use_cases.AddTicketUseCase;
import com.itacademy.sigma_team.tickets.use_cases.DeleteTicketUseCase;
import com.itacademy.sigma_team.tickets.use_cases.GetAllTicketsUseCase;
import com.itacademy.sigma_team.tickets.use_cases.GetTicketUseCase;
import com.itacademy.sigma_team.trees.use_cases.AddTreeUseCase;
import com.itacademy.sigma_team.trees.use_cases.DeleteTreeUseCase;
import com.itacademy.sigma_team.trees.use_cases.GetAllTreesUseCase;

import java.time.LocalDateTime;
import java.util.*;

@SuppressWarnings("FieldCanBeLocal")
public final class CliController {

    //Flowers use cases
    private final AddFlowerUseCase addFlowerUseCase;
    private final DeleteFlowerUseCase deleteFlowerUseCase;
    private final GetAllFlowersUseCase getAllFlowersUseCase;

    // Decoration use cases
    private final AddDecorationUseCase addDecorationUseCase;
    private final DeleteDecorationUseCase deleteDecorationUseCase;
    private final GetAllDecorationsUseCase getAllDecorationsUseCase;

    // Printing use cases
    private final PrintStockUseCase printStockUseCase;

    // Tree use cases
    private final AddTreeUseCase addTreeUseCase;
    private final DeleteTreeUseCase deleteTreeUseCase;
    private final GetAllTreesUseCase getAllTreesUseCase;


    // Ticket use cases
    private final AddTicketUseCase addTicketUseCase;
    private final DeleteTicketUseCase deleteTicketUseCase;
    private final GetTicketUseCase getTicketUseCase;
    private final GetAllTicketsUseCase getAllTicketsUseCase;

    private final Scanner scanner;


    public CliController(AddFlowerUseCase addFlowerUseCase, DeleteFlowerUseCase deleteFlowerUseCase, GetAllFlowersUseCase getAllFlowersUseCase, AddDecorationUseCase addDecorationUseCase, DeleteDecorationUseCase deleteDecorationUseCase, GetAllDecorationsUseCase getAllDecorationsUseCase, PrintStockUseCase printStockUseCase, AddTreeUseCase addTreeUseCase, GetAllTreesUseCase getAllTreesUseCase, AddTicketUseCase addTicketUseCase, DeleteTicketUseCase deleteTicketUseCase, DeleteTreeUseCase deleteTreeUseCase, GetTicketUseCase getTicketUseCase, GetAllTicketsUseCase getAllTicketsUseCase, Scanner scanner) {
        this.addFlowerUseCase = addFlowerUseCase;
        this.deleteFlowerUseCase = deleteFlowerUseCase;
        this.getAllFlowersUseCase = getAllFlowersUseCase;
        this.addDecorationUseCase = addDecorationUseCase;
        this.deleteDecorationUseCase = deleteDecorationUseCase;
        this.getAllDecorationsUseCase = getAllDecorationsUseCase;
        this.printStockUseCase = printStockUseCase;
        this.addTreeUseCase = addTreeUseCase;
        this.getAllTreesUseCase = getAllTreesUseCase;
        this.addTicketUseCase = addTicketUseCase;
        this.deleteTicketUseCase = deleteTicketUseCase;
        this.deleteTreeUseCase = deleteTreeUseCase;
        this.getTicketUseCase = getTicketUseCase;
        this.getAllTicketsUseCase = getAllTicketsUseCase;
        this.scanner = scanner;
    }

    // Flower show entrypoint
    private void createFlowerShop(){}

    // Flowers entry points
    private void addFlower(Flower flower) {
        this.addFlowerUseCase.exec(flower);
    }
    private void deleteFlower(Flower flower) { this.deleteFlowerUseCase.exec(flower); }

    // Trees entry points
    private void addTree(Tree tree) { this.addTreeUseCase.exec(tree); }
    private void deleteTree(Tree tree) { this.deleteTreeUseCase.exec(tree); }

    // Decoration entry points
    private void addDecoration(Decoration decoration) { this.addDecorationUseCase.exec(decoration); }


    // Ticket entry points
    private void addTicket(Ticket ticket) {
        this.addTicketUseCase.exec(ticket);
    }
    private void deleteTicket(Ticket ticket) { this.deleteTicketUseCase.exec(ticket); }

    // Printing entry points
    public void printStock() {
        this.printStockUseCase.exec();
    }
    private void printPurchaseHistory() {}
    private void printBenefits() {}

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
                case 1 -> createFlowerShop();
                case 2 -> addTreeMenu(addTreeUseCase);
                case 3 -> addFlowerMenu(addFlowerUseCase);
                case 4 -> addDecorationMenu(addDecorationUseCase);
                case 5, 9 -> printStock();
                case 6 -> deleteTreeMenu(deleteTreeUseCase, getAllTreesUseCase);
                case 7 -> deleteFlowerMenu(deleteFlowerUseCase, getAllFlowersUseCase);
                case 8 -> deleteDecorationMenu(deleteDecorationUseCase, getAllDecorationsUseCase);
                case 10 -> printBenefits();
                case 11 -> createTicketMenu();
                case 12 -> printPurchaseHistory();
                case 13 -> printBenefits(); // Assuming this method also prints total money earned
                case 14 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createTicketMenu() {

        List<Product> flowers = List.copyOf(getAllFlowersUseCase.exec());
        List<Product> trees = List.copyOf(getAllTreesUseCase.exec());
        List<Product> decorations = List.copyOf(getAllDecorationsUseCase.exec());

        List<Product> selectedItems = new ArrayList<>();

        System.out.println("Select flowers to add to the ticket (type 'done' when finished):");
        printProductDetails(flowers);
        selectItems(flowers, selectedItems);

        System.out.println("Select trees to add to the ticket (type 'done' when finished):");
        printProductDetails(trees);
        selectItems(trees, selectedItems);

        System.out.println("Select decorations to add to the ticket (type 'done' when finished):");
        printProductDetails(decorations);
        selectItems(decorations, selectedItems);

        Ticket ticket = new Ticket(UUID.randomUUID().toString(), LocalDateTime.now(), selectedItems);
        this.addTicketUseCase.exec(ticket);

        System.out.println("Ticket created successfully!");

    }

    private void selectItems(List<Product> items, List<Product> selectedItems) {

        while (true) {

            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            try {

                int index = Integer.parseInt(input) - 1;

                if (index >= 0 && index < items.size()) {

                    selectedItems.add(items.get(index));
                    System.out.println(items.get(index).getClass().getSimpleName() + " added to ticket.");

                } else {
                    System.out.println("Invalid selection. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'done'.");
            }

        }

    }

    private void printProductDetails(List<Product> products) {

        for (int i = 0; i < products.size(); i++) {

            Product product = products.get(i);
            String details = switch (product.getClass().getSimpleName()) {

                case "Flower" -> {
                    Flower flower = (Flower) product;
                    yield (i + 1) + ". " + flower.getName() + " - Color: " + flower.getColor() + " - €" + flower.getPrice();
                }

                case "Tree" -> {
                    Tree tree = (Tree) product;
                    yield (i + 1) + ". " + tree.getName() + " - Height: " + tree.getHeight() + "m - €" + tree.getPrice();
                }

                case "Decoration" -> {
                    Decoration decoration = (Decoration) product;
                    yield (i + 1) + ". " + decoration.getName() + " - Material: " + decoration.getMaterial() + " - €" + decoration.getPrice();
                }

                default -> throw new IllegalStateException("Unexpected value: " + product.getClass().getSimpleName());

            };

            System.out.println(details);

        }
    }

    private void deleteDecorationMenu(DeleteDecorationUseCase deleteDecorationUseCase, GetAllDecorationsUseCase getAllDecorationsUseCase) {

        List<Decoration> decorations = List.copyOf(getAllDecorationsUseCase.exec());
        if (decorations.isEmpty()) {
            throw new IllegalStateException("No decorations available to delete.");
        }

        System.out.println("Available decorations:");
        for (Decoration decoration : decorations) {
            System.out.println(decoration);
        }

        System.out.print("Enter decoration ID to delete: ");
        String id = scanner.nextLine();
        Decoration decorationToDelete = decorations.stream()
                .filter(decoration -> decoration.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (decorationToDelete != null) {
            deleteDecorationUseCase.exec(decorationToDelete);
            System.out.println("Decoration deleted successfully!");
        } else {
            System.out.println("Decoration not found.");
        }

    }

    private void deleteFlowerMenu(DeleteFlowerUseCase deleteFlowerUseCase, GetAllFlowersUseCase getAllFlowersUseCase) {

        List<Flower> flowers = List.copyOf(getAllFlowersUseCase.exec());
        if (flowers.isEmpty()) {
            System.out.println("No flowers available to delete.");
            return;
        }

        System.out.println("Available flowers:");
        for (Flower flower : flowers) {
            System.out.println(flower);
        }

        System.out.print("Enter flower ID to delete: ");
        String id = scanner.nextLine();
        Flower flowerToDelete = flowers.stream()
                .filter(flower -> flower.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (flowerToDelete != null) {
            deleteFlowerUseCase.exec(flowerToDelete);
            System.out.println("Flower deleted successfully!");
        } else {
            System.out.println("Flower not found.");
        }

    }

    private void deleteTreeMenu(DeleteTreeUseCase deleteTreeUseCase, GetAllTreesUseCase getAllTreesUseCase) {

        List<Tree> trees = List.copyOf(getAllTreesUseCase.exec());
        if (trees.isEmpty()) {
            System.out.println("No trees available to delete.");
            return;
        }

        System.out.println("Available trees:");
        for (Tree tree : trees) {
            System.out.println(tree);
        }

        System.out.print("Enter tree ID to delete: ");
        String id = scanner.nextLine();
        Tree treeToDelete = trees.stream()
                .filter(tree -> tree.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (treeToDelete != null) {
            deleteTreeUseCase.exec(treeToDelete);
            System.out.println("Tree deleted successfully!");
        } else {
            System.out.println("Tree not found.");
        }

    }

    private void addDecorationMenu(AddDecorationUseCase addDecorationUseCase) {
        System.out.println("Enter decoration details:");
        System.out.print("Enter decoration ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter decoration name: ");
        String name = scanner.nextLine();
        System.out.print("Enter decoration price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter decoration stock: ");
        int stock = scanner.nextInt();
        Decoration decoration = new Decoration(id, name, null, price, stock);
        addDecorationUseCase.exec(decoration);
        System.out.println("Decoration added successfully!");
    }

    private void addFlowerMenu(AddFlowerUseCase addFlowerUseCase) {

        System.out.println("Enter flower details:");
        System.out.print("Enter flower ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter flower name: ");
        String name = scanner.nextLine();

        System.out.print("Enter flower color: ");
        String color = scanner.nextLine();

        System.out.print("Enter flower price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter flower stock: ");
        int stock = scanner.nextInt();

        scanner.nextLine();

        Flower flower = new Flower(id, name, color, price, stock);
        addFlowerUseCase.exec(flower);

        System.out.println("Flower added successfully!");

    }

    private void addTreeMenu(AddTreeUseCase addTreeUseCase) {

        boolean finishedInput = false;
        while (!finishedInput) {
            try {

                System.out.println("Enter tree details:");

                System.out.print("Enter tree name: ");
                String name = scanner.nextLine();

                System.out.print("Enter tree height: ");
                double height = scanner.nextDouble();

                System.out.print("Enter tree price: ");
                double price = scanner.nextDouble();

                System.out.print("Enter tree stock: ");
                int stock = scanner.nextInt();

                Tree tree = new Tree(UUID.randomUUID().toString(), name, height, price, stock);
                addTreeUseCase.exec(tree);

                System.out.println("Tree added successfully!");
                finishedInput = true;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input format. Please try again.");
                scanner.nextLine(); // Clear the buffer
            }

        }

    }

}
