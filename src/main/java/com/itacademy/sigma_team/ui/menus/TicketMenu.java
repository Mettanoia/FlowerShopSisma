package com.itacademy.sigma_team.ui.menus;

import com.itacademy.sigma_team.common.Adder;
import com.itacademy.sigma_team.domain.*;
import com.itacademy.sigma_team.dtos.TicketItem;
import com.itacademy.sigma_team.tickets.repositories.TicketMappers;
import com.itacademy.sigma_team.ui.CrudControllerAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TicketMenu {

    private final Supplier<Collection<Ticket>> getAllTicketsUseCase;
    private final Supplier<Collection<Flower>> getAllFlowersUseCase;
    private final Supplier<Collection<Tree>> getAllTreesUseCase;
    private final Supplier<Collection<Decoration>> getAllDecorationsUseCase;
    private final Adder<Ticket> addTicketUseCase;
    private final Consumer<Ticket> deleteTicketUseCase;
    private final MenuUtils menuUtils;

    public TicketMenu(
            CrudControllerAdapter<Ticket, String> ticketController,
            CrudControllerAdapter<Decoration, String> decorationController,
            CrudControllerAdapter<Tree, String> treeController,
            CrudControllerAdapter<Flower, String> flowerController
    ) {

        this.getAllTicketsUseCase = ticketController;
        this.deleteTicketUseCase = ticketController;
        this.addTicketUseCase = ticketController;
        this.getAllFlowersUseCase = flowerController;
        this.getAllTreesUseCase = treeController;
        this.getAllDecorationsUseCase = decorationController;
        this.menuUtils = MenuUtils.getInstance();

    }

    public void showMenu() {


        AtomicInteger selectedIndex = new AtomicInteger(0);
        Scanner scanner = new Scanner(System.in);

        while (true) {

            List<Ticket> tickets = List.copyOf(getAllTicketsUseCase.get());
            menuUtils.clearScreen();
            menuUtils.printHeader("Ticket Menu");
            printTable(tickets, selectedIndex.get());
            menuUtils.printFooter();

            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "w":
                    if (selectedIndex.get() > 0) {
                        selectedIndex.decrementAndGet();
                    }
                    break;
                case "s":
                    if (selectedIndex.get() < tickets.size() - 1) {
                        selectedIndex.incrementAndGet();
                    }
                    break;

                case "enter":
                    menuUtils.clearScreen();
                    System.out.println(colorText("You selected: " + formatTicket(tickets.get(selectedIndex.get())), "\033[0;34m"));
                    return;

                case "a":
                    menuUtils.clearScreen();
                    addNewTicket(scanner, tickets);
                    break;

                case "d":
                    menuUtils.clearScreen();
                    deleteSelectedTicket(tickets, selectedIndex.get());
                    if (selectedIndex.get() >= tickets.size() && selectedIndex.get() > 0) {
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

                        if (index >= 0 && index < tickets.size()) {
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

    private void addNewTicket(Scanner scanner, List<Ticket> tickets) {
        try {

            List<Product> products = new ArrayList<>();
            boolean addingProducts = true;

            while (addingProducts) {

                menuUtils.clearScreen();
                System.out.println("Choose product type to add:");
                System.out.println("1. Tree");
                System.out.println("2. Flower");
                System.out.println("3. Decoration");
                System.out.println("4. Done adding products");

                String productTypeChoice = scanner.nextLine();
                switch (productTypeChoice) {
                    case "1":
                        products.add(selectProduct(scanner, getAllTreesUseCase.get()));
                        break;
                    case "2":
                        products.add(selectProduct(scanner, getAllFlowersUseCase.get()));
                        break;
                    case "3":
                        products.add(selectProduct(scanner, getAllDecorationsUseCase.get()));
                        break;
                    case "4":
                        addingProducts = false;
                        break;
                    default:
                        System.out.println(colorText("Invalid choice. Please try again.", "\033[0;31m"));
                }
            }

            addTicketUseCase.add(new Ticket(UUID.randomUUID().toString(), LocalDateTime.now(), products));
            System.out.println(colorText("Ticket added successfully.", "\033[0;32m"));

        } catch (DateTimeParseException e) {
            System.out.println(colorText("Invalid date and time format. Please try again.", "\033[0;31m"));
            addNewTicket(scanner, tickets);
        }
    }

    private <T extends Product> T selectProduct(Scanner scanner, Collection<T> products) {

        List<T> productList = new ArrayList<>(products);
        AtomicInteger selectedIndex = new AtomicInteger(0);

        while (true) {

            menuUtils.clearScreen();
            System.out.println("Select a product:");
            printProductTable(productList, selectedIndex.get());
            System.out.println("Enter index to select, 'w' to move up, 's' to move down, 'enter' to confirm:");

            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "w":
                    if (selectedIndex.get() > 0) {
                        selectedIndex.decrementAndGet();
                    }
                    break;
                case "s":
                    if (selectedIndex.get() < productList.size() - 1) {
                        selectedIndex.incrementAndGet();
                    }
                    break;
                case "enter":
                    return productList.get(selectedIndex.get());
                default:
                    try {
                        int index = Integer.parseInt(input);
                        if (index >= 0 && index < productList.size()) {
                            selectedIndex.set(index);
                        } else {
                            System.out.println(colorText("Invalid index. Please try again.", "\033[0;31m"));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(colorText("Invalid input. Please use 'w', 's', 'enter', or a valid index.", "\033[0;31m"));
                    }
                    break;
            }
        }
    }

    private <T extends Product> void printProductTable(List<T> products, int selectedIndex) {

        System.out.printf("%-4s | %-20s | %-15s | %-10s%n", "Idx", "Name", "Details", "Price");
        System.out.println("----------------------------------------------------------");

        for (int i = 0; i < products.size(); i++) {
            T product = products.get(i);
            String details = "";
            if (product instanceof Tree) {
                details = "Height: " + ((Tree) product).getHeight();
            } else if (product instanceof Flower) {
                details = "Color: " + ((Flower) product).getColor();
            } else if (product instanceof Decoration) {
                details = "Material: " + ((Decoration) product).getMaterial();
            }

            String line = String.format("%-4d | %-20s | %-15s | %-10.2f", i, Product.getName(product), details, Product.getPrice(product));
            if (i == selectedIndex) {
                System.out.println(colorText(">> " + line, "\033[0;32m")); // Green for selected
            } else {
                System.out.println("   " + line);
            }
        }
    }


    private void deleteSelectedTicket(List<Ticket> tickets, int selectedIndex) {
        if (tickets.isEmpty()) {
            System.out.println(colorText("No tickets to delete.", "\033[0;31m")); // Red for error message
        } else {
            deleteTicketUseCase.accept(tickets.get(selectedIndex));
            System.out.println(colorText("Ticket deleted successfully.", "\033[0;32m")); // Green for success message
        }
    }

    private void printTable(List<Ticket> tickets, int selectedIndex) {
        System.out.printf("%-4s | %-36s | %-25s | %-5s%n", "Idx", "ID", "DateTime", "Items");
        System.out.println("----------------------------------------------------------------------------------");
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            String line = String.format("%-4d | %-36s | %-25s | %-5d", i, ticket.getId(), ticket.getDateTime(), ticket.getItems().size());
            if (i == selectedIndex) {
                System.out.println(colorText(">> " + line, "\033[0;32m")); // Green for selected
            } else {
                System.out.println("   " + line);
            }
        }
    }

    private String formatTicket(Ticket ticket) {

        StringBuilder ticketDetails = new StringBuilder();
        double totalPrice = 0.0;

        ticketDetails.append(String.format("%s - DateTime: %s [%d items]",
                colorText(ticket.getId(), "\033[1;34m"),
                colorText(ticket.getDateTime().toString(), "\033[1;35m"),
                ticket.getItems().size()));

        // Contar los productos repetidos
        Map<String, Integer> productCount = ticket.getItems().stream()
                .collect(
                        Collectors.toMap(Product::getId, e -> 1, Integer::sum)
                );

        // Imprimir productos con sus cantidades
        for (Product product : Set.copyOf(ticket.getItems())) {

            int quantity = productCount.get(Product.getId(product));

            if (product instanceof Flower flower) {

                ticketDetails.append(String.format("\n%s - Name: %s - Color: %s - Quantity: %d - Price: %.2f",
                        colorText(flower.getId(), "\033[1;32m"),
                        colorText(flower.getName(), "\033[1;33m"),
                        colorText(flower.getColor(), "\033[1;36m"),
                        quantity,
                        flower.getPrice() * quantity));

            } else if (product instanceof Tree tree) {

                ticketDetails.append(String.format("\n%s - Name: %s - Height: %.2f - Quantity: %d - Price: %.2f",
                        tree.getId(),
                        tree.getName(),
                        tree.getHeight(),
                        quantity,
                        tree.getPrice() * quantity));

            } else {
                if (product instanceof Decoration decoration) {

                    ticketDetails.append(String.format("\n%s - Name: %s - Material: %s - Quantity: %d - Price: %.2f",
                            colorText(decoration.getId(), "\033[1;32m"),
                            colorText(decoration.getName(), "\033[1;33m"),
                            colorText(decoration.getMaterial().toString(), "\033[1;36m"),
                            quantity,
                            decoration.getPrice() * quantity));
                }
            }

            totalPrice += Product.getPrice(product) * quantity; // Sumar el precio del producto al total
        }

        ticketDetails.append("\n");
        // A�adir el precio total al final del ticket
        ticketDetails.append(String.format("\nTotal Price: %.2f", totalPrice));

        return ticketDetails.toString();
    }


    private String colorText(String text, String colorCode) {
        return colorCode + text + "\033[0m";
    }

    private static void unsupportedOperation() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
