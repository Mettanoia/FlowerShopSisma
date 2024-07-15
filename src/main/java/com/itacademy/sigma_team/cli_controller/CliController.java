package com.itacademy.sigma_team.cli_controller;

import com.itacademy.sigma_team.decorations.use_cases.AddDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.DeleteDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.GetAllDecorationsUseCase;
import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Ticket;
import com.itacademy.sigma_team.domain.Tree;
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

    public CliController(AddFlowerUseCase addFlowerUseCase, DeleteFlowerUseCase deleteFlowerUseCase,
                         GetAllFlowersUseCase getAllFlowersUseCase, AddDecorationUseCase addDecorationUseCase,
                         DeleteDecorationUseCase deleteDecorationUseCase, GetAllDecorationsUseCase getAllDecorationsUseCase,
                         PrintStockUseCase printStockUseCase, AddTreeUseCase addTreeUseCase,
                         GetAllTreesUseCase getAllTreesUseCase, AddTicketUseCase addTicketUseCase,
                         DeleteTicketUseCase deleteTicketUseCase, DeleteTreeUseCase deleteTreeUseCase,
                         GetTicketUseCase getTicketUseCase, GetAllTicketsUseCase getAllTicketsUseCase) {
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
    }

    // Flower show entrypoint
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
        Collection<Flower> flowerCollection = getAllFlowersUseCase.exec();
        if (flowerCollection.isEmpty()) {
            System.out.println("No flowers available to delete.");
        } else {
            List<Flower> flowers = new ArrayList<>(flowerCollection);
            System.out.println("Select a flower to delete:");
            for (int i = 0; i < flowers.size(); i++) {
                Flower flower = flowers.get(i);
                System.out.printf("%d. %s (Color: %s, Price: %.2f, Stock: %d)\n", i + 1, flower.getName(), flower.getColor(), flower.getPrice(), flower.getStock());
            }

            System.out.print("Enter the number of the flower to delete: ");
            int index = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline

            if (index < 0 || index >= flowers.size()) {
                System.out.println("Invalid selection. Please try again.");
            } else {
                Flower flowerToDelete = flowers.get(index);
                this.deleteFlowerUseCase.exec(flowerToDelete);
                System.out.println("Flower deleted successfully.");
            }
        }
    }

    // Trees entry points
    private void addTree(Tree tree) { this.addTreeUseCase.exec(tree); }
    private void deleteTree(Tree tree) { this.deleteTreeUseCase.exec(tree); }

    // Decoration entry points
    private void addDecoration(Decoration decoration) { this.addDecorationUseCase.exec(decoration); }


    // Ticket entry points
    private void addTicket(Ticket ticket) { this.addTicketUseCase.exec(ticket); }
    private void deleteTicket(Ticket ticket) { this.deleteTicketUseCase.exec(ticket); }

    // Printing entry points
    public void printStock() { this.printStockUseCase.exec(); }
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
                case 2 -> addTree();
                case 3 -> addFlower();
                case 4 -> addDecoration();
                case 5 -> printStock();
                case 6 -> deleteTree();
                case 7 -> deleteFlower();
                case 8 -> deleteDecoration();
                case 10 -> printBenefits();
                case 11 -> createTicket();
                case 12 -> printPurchaseHistory();
                case 13 -> printBenefits();
                case 14 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

    private void deleteDecorationMenu(DeleteDecorationUseCase deleteDecorationUseCase) {
    }

    private void deleteFlowerMenu(DeleteFlowerUseCase deleteFlowerUseCase) {
    }

    private void deleteTreeMenu(DeleteTreeUseCase deleteTreeUseCase, GetAllTreesUseCase getAllTreesUseCase) {
        List<Tree> trees = (List<Tree>) getAllTreesUseCase.exec();
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

    private void addDecorationMenu(AddFlowerUseCase addFlowerUseCase) {
    }

    private void addFlowerMenu(AddFlowerUseCase addFlowerUseCase) {
    }

    private void addTreeMenu(AddTreeUseCase addTreeUseCase) {
        System.out.println("Enter tree details:");
        System.out.print("Enter tree ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter tree name: ");
        String name = scanner.nextLine();
        System.out.print("Enter tree height: ");
        double height = scanner.nextDouble();
        System.out.print("Enter tree price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter tree stock: ");
        int stock = scanner.nextInt();
        Tree tree = new Tree(id, name, height, price, stock);
        addTreeUseCase.exec(tree);
        System.out.println("Tree added successfully!");
    }

}
