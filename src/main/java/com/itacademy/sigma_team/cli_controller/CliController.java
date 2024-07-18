package com.itacademy.sigma_team.cli_controller;

import com.itacademy.sigma_team.decorations.use_cases.AddDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.DeleteDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.GetAllDecorationsUseCase;
import com.itacademy.sigma_team.domain.*;
import com.itacademy.sigma_team.flower_shop.FlowerShop;
import com.itacademy.sigma_team.flower_shop.use_cases.UpdateFlowerShopUseCase;
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

    // FlowerShop use case
    private final UpdateFlowerShopUseCase updateFlowerShopUseCase;

    // Utilities
    private final Scanner scanner;
    private FlowerShop flowerShop;

    public CliController(AddFlowerUseCase addFlowerUseCase, DeleteFlowerUseCase deleteFlowerUseCase, GetAllFlowersUseCase getAllFlowersUseCase, AddDecorationUseCase addDecorationUseCase, DeleteDecorationUseCase deleteDecorationUseCase, GetAllDecorationsUseCase getAllDecorationsUseCase, PrintStockUseCase printStockUseCase, AddTreeUseCase addTreeUseCase, GetAllTreesUseCase getAllTreesUseCase, AddTicketUseCase addTicketUseCase, DeleteTicketUseCase deleteTicketUseCase, DeleteTreeUseCase deleteTreeUseCase, GetTicketUseCase getTicketUseCase, GetAllTicketsUseCase getAllTicketsUseCase, UpdateFlowerShopUseCase updateFlowerShopUseCase, Scanner scanner, FlowerShop flowerShop) {
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
        this.updateFlowerShopUseCase = updateFlowerShopUseCase;
        this.scanner = scanner;
        this.flowerShop = flowerShop;
    }

    private FlowerShop getFlowerShop() {
        return flowerShop;
    }

    private void setFlowerShop(FlowerShop flowerShop) {
        this.flowerShop = flowerShop;
    }

    private void createFlowerShop(){}

    // Printing entry points
    public void printStock() {
        this.printStockUseCase.exec(getFlowerShop());
    }

    private void printPurchaseHistory() {
        List<Ticket> tickets = List.copyOf(getAllTicketsUseCase.exec());
        for (Ticket ticket : tickets) {
            printTicket(ticket);
        }
        System.out.print(ANSI_YELLOW + "Press any key to continue..." + ANSI_RESET);
        scanner.nextLine(); // Wait for user input
    }

    private void printTicket(Ticket ticket) {
        System.out.println(ANSI_BLUE + "Ticket ID: " + ANSI_RESET + ticket.getId());
        System.out.println(ANSI_BLUE + "Date: " + ANSI_RESET + ticket.getDateTime());
        System.out.println(ANSI_BLUE + "Items:" + ANSI_RESET);

        double total = 0.0;

        for (Product item : ticket.getItems()) {
            total += printItem(item);
        }

        System.out.println(ANSI_GREEN + "Total: €" + total + ANSI_RESET);
        System.out.println("=".repeat(50));
    }

    private double printItem(Product item) {
        double price = Product.getPrice(item);
        int stock = Product.getStock(item);
        double totalPrice = price * stock;

        System.out.printf(
                ANSI_CYAN + "ID: " + ANSI_RESET + "%s, " +
                        ANSI_GREEN + "Name: " + ANSI_RESET + "%s, " +
                        ANSI_YELLOW + "Price: " + ANSI_RESET + "€%.2f, " +
                        ANSI_RED + "Quantity: " + ANSI_RESET + "%d, " +
                        ANSI_PURPLE + "Total Price: " + ANSI_RESET + "€%.2f\n",
                Product.getId(item),
                Product.getName(item),
                price,
                stock,
                totalPrice
        );

        return totalPrice;
    }

    private void printBenefits() {}

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";

    public void displayMenu() {

        final Scanner scanner = new Scanner(System.in);

        while (true) {
            clearConsole();

            String frameColor = ANSI_BLUE;
            System.out.println(frameColor + "╔═════════════════════════════════╗" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_CYAN + "           Main Menu             " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "╠═════════════════════════════════╣" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_GREEN + " 1. Create Flower Shop           " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_GREEN + " 2. Add Tree                     " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_GREEN + " 3. Add Flower                   " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_GREEN + " 4. Add Decoration               " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_GREEN + " 5. Show Stock                   " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_GREEN + " 6. Remove Tree                  " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_GREEN + " 7. Remove Flower                " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_GREEN + " 8. Remove Decoration            " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_GREEN + " 9. Print Stock with Quantities  " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_GREEN + "10. Print Total Value of Shop    " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_GREEN + "11. Create Purchase Ticket       " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_GREEN + "12. Show Purchase History        " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_GREEN + "13. View Total Money Earned      " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "║" + ANSI_RESET + ANSI_RED + "14. Exit                         " + frameColor + "║" + ANSI_RESET);
            System.out.println(frameColor + "╚═════════════════════════════════╝" + ANSI_RESET);
            System.out.print(ANSI_CYAN + "Select an option: " + ANSI_RESET);

            int option = -1;
            boolean validInput = false;

            while (!validInput) {
                if (scanner.hasNextInt()) {
                    option = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    if (option >= 1 && option <= 14) {
                        validInput = true;
                    } else {
                        System.out.println(ANSI_RED + "Invalid option. Please try again." + ANSI_RESET);
                        System.out.print(ANSI_CYAN + "Select an option: " + ANSI_RESET);
                    }
                } else {
                    System.out.println(ANSI_RED + "Invalid input. Please enter a number." + ANSI_RESET);
                    System.out.print(ANSI_CYAN + "Select an option: " + ANSI_RESET);
                    scanner.next(); // Consume the invalid input
                }
            }

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
                    System.out.println(ANSI_RED + "Exiting..." + ANSI_RESET);
                    return;
                }
                default -> System.out.println(ANSI_RED + "This should never happen." + ANSI_RESET);
            }
        }
    }

    public void clearConsole() {
        try {
            // Clear console using ANSI escape codes
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
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
        setFlowerShop(updateFlowerShopUseCase.exec(getFlowerShop()));
        System.out.println("Ticket created successfully!");

    }

    private void selectItems(List<Product> items, List<Product> selectedItems) {

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";

        while (true) {
            System.out.println("Enter the number of the product to add to the ticket (type 'done' when finished):");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            try {
                int index = Integer.parseInt(input) - 1;

                if (index >= 0 && index < items.size()) {
                    Product product = items.get(index);

                    if (Product.getStock(product) > 0) {
                        selectedItems.add(product);
                        System.out.println(product.getClass().getSimpleName() + " added to ticket.");
                    } else {
                        System.out.println(ANSI_RED + "Cannot select a product with zero stock." + ANSI_RESET);
                    }
                } else {
                    System.out.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'done'.");
            }

            // Debugging: print the selected items so far
            System.out.println("Selected items so far:");
            for (int i = 0; i < selectedItems.size(); i++) {
                Product selectedItem = selectedItems.get(i);
                System.out.println((i + 1) + ". " + Product.getName(selectedItem) + " - Stock: " + Product.getStock(selectedItem));
            }
        }
    }

    private void printProductDetails(List<Product> products) {

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";

        for (int i = 0; i < products.size(); i++) {

            Product product = products.get(i);
            String stockColor = Product.getStock(product) > 0 ? ANSI_RESET : ANSI_RED;

            String details = switch (product.getClass().getSimpleName()) {
                case "Flower" -> {
                    Flower flower = (Flower) product;
                    yield (i + 1) + ". " + flower.getName() + " - Color: " + flower.getColor() + " - €" + flower.getPrice() + " - " + stockColor + "Stock: " + flower.getStock() + ANSI_RESET;
                }
                case "Tree" -> {
                    Tree tree = (Tree) product;
                    yield (i + 1) + ". " + tree.getName() + " - Height: " + tree.getHeight() + "m - €" + tree.getPrice() + " - " + stockColor + "Stock: " + tree.getStock() + ANSI_RESET;
                }
                case "Decoration" -> {
                    Decoration decoration = (Decoration) product;
                    yield (i + 1) + ". " + decoration.getName() + " - Material: " + decoration.getMaterial() + " - €" + decoration.getPrice() + " - " + stockColor + "Stock: " + decoration.getStock() + ANSI_RESET;
                }
                default -> throw new IllegalStateException("Unexpected value: " + product.getClass().getSimpleName());
            };

            System.out.println(details);
        }
    }

    private void deleteDecorationMenu(DeleteDecorationUseCase deleteDecorationUseCase, GetAllDecorationsUseCase getAllDecorationsUseCase) {

        try {

            List<Decoration> decorations = List.copyOf(getAllDecorationsUseCase.exec());

            if (decorations.isEmpty()) {
                throw new IllegalStateException("No decorations available to delete.");
            }

            System.out.println(ANSI_BLUE + "Available decorations:" + ANSI_RESET + "\n");

            for (Decoration decoration : decorations) {
                System.out.println(" - " + ANSI_PURPLE + "ID: " + ANSI_RESET + decoration.getId() + ", " +
                        ANSI_GREEN + "Name: " + ANSI_RESET + decoration.getName() + ", " +
                        ANSI_CYAN + "Material: " + ANSI_RESET + decoration.getMaterial() + ", " +
                        ANSI_YELLOW + "Price: " + ANSI_RESET + "€" + decoration.getPrice() + ", " +
                        ANSI_RED + "Stock: " + ANSI_RESET + decoration.getStock());
            }

            System.out.println();

            System.out.print(ANSI_YELLOW + "Enter decoration ID to delete: " + ANSI_RESET);
            String id = scanner.nextLine();

            Decoration decorationToDelete = decorations.stream()
                    .filter(decoration -> decoration.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (decorationToDelete != null) {

                deleteDecorationUseCase.exec(decorationToDelete);
                setFlowerShop(updateFlowerShopUseCase.exec(getFlowerShop())); // Update the model
                System.out.println(ANSI_GREEN + "Decoration deleted successfully!" + ANSI_RESET);

            } else {
                System.out.println(ANSI_RED + "Decoration not found." + ANSI_RESET);
            }

        } catch (IllegalStateException e) {
            System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
        } catch (NoSuchElementException e) {
            System.out.println(ANSI_RED + "Invalid input. Please enter a valid decoration ID." + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(ANSI_RED + "An unexpected error occurred: " + e.getMessage() + ANSI_RESET);
        }
    }

    private void deleteFlowerMenu(DeleteFlowerUseCase deleteFlowerUseCase, GetAllFlowersUseCase getAllFlowersUseCase) {
        try {
            List<Flower> flowers = List.copyOf(getAllFlowersUseCase.exec());
            if (flowers.isEmpty()) {
                throw new IllegalStateException("No flowers available to delete.");
            }

            System.out.println("Available flowers:");
            for (Flower flower : flowers) {
                System.out.printf(
                        ANSI_CYAN + "ID: " + ANSI_RESET + "%s, " +
                                ANSI_GREEN + "Name: " + ANSI_RESET + "%s, " +
                                ANSI_YELLOW + "Color: " + ANSI_RESET + "%s, " +
                                ANSI_PURPLE + "Price: " + ANSI_RESET + "€%.2f, " +
                                ANSI_RED + "Stock: " + ANSI_RESET + "%d\n",
                        flower.getId(),
                        flower.getName(),
                        flower.getColor(),
                        flower.getPrice(),
                        flower.getStock()
                );
            }

            System.out.print("Enter flower ID to delete: ");
            String id = scanner.nextLine();
            Flower flowerToDelete = flowers.stream()
                    .filter(flower -> flower.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (flowerToDelete != null) {
                deleteFlowerUseCase.exec(flowerToDelete);
                setFlowerShop(updateFlowerShopUseCase.exec(getFlowerShop())); // Update the model
                System.out.println("Flower deleted successfully!");
            } else {
                System.out.println("Flower not found.");
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Invalid input. Please enter a valid flower ID.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void deleteTreeMenu(DeleteTreeUseCase deleteTreeUseCase, GetAllTreesUseCase getAllTreesUseCase) {
        try {
            List<Tree> trees = List.copyOf(getAllTreesUseCase.exec());
            if (trees.isEmpty()) {
                throw new IllegalStateException("No trees available to delete.");
            }

            System.out.println("Available trees:");
            for (Tree tree : trees) {
                System.out.println(" - " + ANSI_PURPLE + "ID: " + ANSI_RESET + tree.getId() + ", " +
                        ANSI_GREEN + "Name: " + ANSI_RESET + tree.getName() + ", " +
                        ANSI_CYAN + "Height: " + ANSI_RESET + tree.getHeight() + ", " +
                        ANSI_YELLOW + "Price: " + ANSI_RESET + "€" + tree.getPrice() + ", " +
                        ANSI_RED + "Stock: " + ANSI_RESET + tree.getStock());
            }

            System.out.print("Enter tree ID to delete: ");
            String id = scanner.nextLine();
            Tree treeToDelete = trees.stream()
                    .filter(tree -> tree.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (treeToDelete != null) {
                deleteTreeUseCase.exec(treeToDelete);
                setFlowerShop(updateFlowerShopUseCase.exec(getFlowerShop())); // Update the model
                System.out.println("Tree deleted successfully!");
            } else {
                System.out.println("Tree not found.");
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Invalid input. Please enter a valid tree ID.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }

    }

    private void addDecorationMenu(AddDecorationUseCase addDecorationUseCase) {

        final Scanner scanner = new Scanner(System.in);

        boolean finishedInput = false;
        while (!finishedInput) {

            try {
                System.out.println(ANSI_BLUE + "Enter decoration details:" + ANSI_RESET);

                System.out.print(ANSI_CYAN + "Enter decoration name: " + ANSI_RESET);
                String name = scanner.nextLine();

                System.out.print(ANSI_CYAN + "Enter decoration price: " + ANSI_RESET);
                double price = scanner.nextDouble();

                System.out.print(ANSI_CYAN + "Enter decoration stock: " + ANSI_RESET);
                int stock = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                Material material = null;
                boolean validMaterial = false;

                while (!validMaterial) {
                    System.out.print(ANSI_CYAN + "Enter decoration material (" + ANSI_YELLOW + getValidMaterials() + ANSI_CYAN + "): " + ANSI_RESET);
                    String materialInput = scanner.nextLine().toUpperCase();

                    try {
                        material = Material.valueOf(materialInput);
                        validMaterial = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println(ANSI_RED + "Invalid material. Please enter one of the following: " + ANSI_YELLOW + getValidMaterials() + ANSI_RED + "." + ANSI_RESET);
                    }
                }

                Decoration decoration = new Decoration(UUID.randomUUID().toString(), name, material, price, stock);
                addDecorationUseCase.exec(decoration);
                setFlowerShop(updateFlowerShopUseCase.exec(getFlowerShop())); // Update the model

                System.out.println(ANSI_GREEN + "Decoration added successfully!" + ANSI_RESET);
                finishedInput = true;

            } catch (Exception e) {
                System.out.println(ANSI_RED + "An error occurred: " + e.getMessage() + ANSI_RESET);
            }
        }

    }

    private static String getValidMaterials() {
        StringBuilder validMaterials = new StringBuilder();
        for (Material material : Material.values()) {
            if (validMaterials.length() > 0) {
                validMaterials.append("/");
            }
            validMaterials.append(material.name());
        }
        return validMaterials.toString();
    }

    private void addFlowerMenu(AddFlowerUseCase addFlowerUseCase) {
        boolean finishedInput = false;
        while (!finishedInput) {
            try {
                System.out.println(ANSI_BLUE + "Enter flower details:" + ANSI_RESET);

                System.out.print(ANSI_CYAN + "Enter flower name: " + ANSI_RESET);
                String name = scanner.nextLine();

                System.out.print(ANSI_CYAN + "Enter flower color: " + ANSI_RESET);
                String color = scanner.nextLine();

                System.out.print(ANSI_CYAN + "Enter flower price: " + ANSI_RESET);
                double price = scanner.nextDouble();

                System.out.print(ANSI_CYAN + "Enter flower stock: " + ANSI_RESET);
                int stock = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                Flower flower = new Flower(name, color, price, stock);
                addFlowerUseCase.exec(flower);

                setFlowerShop(updateFlowerShopUseCase.exec(getFlowerShop())); // Update the model

                System.out.println(ANSI_GREEN + "Flower added successfully!" + ANSI_RESET);

                finishedInput = true;

            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Invalid input format. Please try again." + ANSI_RESET);
                scanner.nextLine(); // Clear the buffer
            } catch (Exception e) {
                System.out.println(ANSI_RED + "An error occurred: " + e.getMessage() + ANSI_RESET);
            }

        }
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
                setFlowerShop(updateFlowerShopUseCase.exec(getFlowerShop())); // Update the model
                finishedInput = true;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input format. Please try again.");
                scanner.nextLine(); // Clear the buffer
            }

        }

    }

}
