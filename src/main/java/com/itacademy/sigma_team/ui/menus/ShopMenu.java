package com.itacademy.sigma_team.ui.menus;

import com.itacademy.sigma_team.decorations.repositories.ShopSpecificDecorationRepository;
import com.itacademy.sigma_team.flower_shop.repositories.FlowerShopDTO;
import com.itacademy.sigma_team.flower_shop.repositories.ShopRepository;
import com.itacademy.sigma_team.flowers.repositories.ShopSpecificFlowerRepository;
import com.itacademy.sigma_team.tickets.repositories.ShopSpecificTicketRepository;
import com.itacademy.sigma_team.trees.repositories.ShopSpecificTreeRepository;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Supplier;

public class ShopMenu {

    private final ShopRepository shopRepository;
    private List<String> shopNames;
    private final MenuUtils menuUtils;
    private final FlowerMenu flowerMenu;
    private final TreeMenu treeMenu;
    private final DecorationMenu decorationMenu;
    private final TicketMenu ticketMenu;

    public ShopMenu(FlowerMenu flowerMenu, TreeMenu treeMenu, DecorationMenu decorationMenu, TicketMenu ticketMenu, ShopRepository shopRepository, List<String> shopNames) {
        this.shopRepository = shopRepository;
        this.shopNames = shopNames;
        this.menuUtils = MenuUtils.getInstance();
        this.flowerMenu = flowerMenu;
        this.treeMenu = treeMenu;
        this.decorationMenu = decorationMenu;
        this.ticketMenu = ticketMenu;
    }

    public void showShopMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            menuUtils.clearScreen();
            printHeader();
            printOptions();
            printFooter();

            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "1":
                case "2":
                case "3":

                    int shopIndex = Integer.parseInt(input) - 1;

                    if (shopIndex >= 0 && shopIndex < shopNames.size()) {

                        String selectedShop = shopNames.get(shopIndex);
                        Supplier<String> getSelectedShop = () -> selectedShop;

                        ShopSpecificFlowerRepository.setShopNameSupplier(getSelectedShop);
                        ShopSpecificTreeRepository.setShopNameSupplier(getSelectedShop);
                        ShopSpecificDecorationRepository.setShopNameSupplier(getSelectedShop);
                        ShopSpecificTicketRepository.setShopNameSupplier(getSelectedShop);

                        MainMenu mainMenu = new MainMenu(flowerMenu, treeMenu, decorationMenu, ticketMenu, selectedShop);
                        mainMenu.showMainMenu();
                        return;

                    } else {
                        System.out.println(colorText("Invalid selection. Please try again.", "\033[0;31m"));
                    }
                    break;
                case "c":
                    System.out.println(colorText("Enter new shop name:", "\033[1;33m"));
                    String newShopName = scanner.nextLine();
                    if (!newShopName.trim().isEmpty()) {
                        try {
                            shopRepository.add(new FlowerShopDTO(UUID.randomUUID().toString(), newShopName));
                            shopNames = shopRepository.getAllShopNames();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(colorText("Shop added successfully. Please choose a shop from the list.", "\033[0;32m"));
                    } else {
                        System.out.println(colorText("Invalid name. Please try again.", "\033[0;31m"));
                    }
                    break;
                default:
                    System.out.println(colorText("Invalid choice. Please try again.", "\033[0;31m"));
                    break;
            }
        }
    }

    private void printHeader() {
        System.out.println(colorText("\n\n============================ Select or Create Shop =============================", "\033[1;36m"));
    }

    private void printOptions() {

        System.out.println(colorText("Please choose an existing shop or create a new one:", "\033[1;33m"));

        for (int i = 0; i < shopNames.size(); i++) {
            System.out.println(colorText((i + 1) + ". " + shopNames.get(i), "\033[1;34m")); // Blue for options
        }

        System.out.println(colorText("c. Create new shop", "\033[1;34m"));

    }

    private void printFooter() {
        System.out.println(colorText("\n================================================================================", "\033[1;36m"));

    }

    private String colorText(String text, String colorCode) {
        return colorCode + text + "\033[0m";
    }
}
