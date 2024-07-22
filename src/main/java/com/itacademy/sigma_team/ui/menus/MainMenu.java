package com.itacademy.sigma_team.ui.menus;

import java.util.Scanner;

public class MainMenu {

    private final FlowerMenu flowerMenu;
    private final TreeMenu treeMenu;
    private final DecorationMenu decorationMenu;
    private final TicketMenu ticketMenu;
    private final MenuUtils menuUtils;
    private final String storeName;

    public MainMenu(FlowerMenu flowerMenu, TreeMenu treeMenu, DecorationMenu decorationMenu, TicketMenu ticketMenu, String storeName) {
        this.flowerMenu = flowerMenu;
        this.treeMenu = treeMenu;
        this.decorationMenu = decorationMenu;
        this.ticketMenu = ticketMenu;
        this.menuUtils = MenuUtils.getInstance();
        this.storeName = storeName != null ? storeName : "Our Shop";
    }

    public void showMainMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            menuUtils.clearScreen();
            printHeader();
            printOptions();
            printFooter();

            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "1":
                    ticketMenu.showMenu();
                    break;
                case "2":
                    treeMenu.showMenu();
                    break;
                case "3":
                    decorationMenu.showMenu();
                    break;
                case "4":
                    flowerMenu.showMenu();
                    break;
                case "q":
                    menuUtils.clearScreen();
                    System.out.println(colorText("Thank you for visiting " + storeName + "!", "\033[0;32m"));
                    return;
                default:
                    System.out.println(colorText("Invalid choice. Please try again.", "\033[0;31m"));
                    break;
            }
        }
    }

    private void printHeader() {
        System.out.println(colorText("\n\n============================== " + storeName + " ==============================", "\033[1;36m")); // Cyan for header
    }

    private void printOptions() {
        System.out.println(colorText("Welcome to " + storeName + "! Please choose an option:", "\033[1;33m")); // Yellow for instructions
        System.out.println(colorText("1. Tickets Menu", "\033[1;34m")); // Blue for options
        System.out.println(colorText("2. Trees Menu", "\033[1;34m"));
        System.out.println(colorText("3. Decorations Menu", "\033[1;34m"));
        System.out.println(colorText("4. Flowers Menu", "\033[1;34m"));
        System.out.println(colorText("q. Quit", "\033[1;31m")); // Red for quit
    }

    private void printFooter() {
        System.out.println(colorText("\n==================================================================", "\033[1;36m")); // Cyan for footer
    }

    private String colorText(String text, String colorCode) {
        return colorCode + text + "\033[0m";
    }
}
