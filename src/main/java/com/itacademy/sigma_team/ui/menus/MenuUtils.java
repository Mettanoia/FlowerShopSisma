package com.itacademy.sigma_team.ui.menus;

public class MenuUtils {

    private static MenuUtils instance;

    private MenuUtils() {
        // private constructor to prevent instantiation
    }

    public static synchronized MenuUtils getInstance() {
        if (instance == null) {
            instance = new MenuUtils();
        }
        return instance;
    }

    public void printHeader(String title) {
        System.out.println(colorText("\n\n=========================== " + title + " ===========================", "\033[1;36m")); // Cyan for header
        System.out.println(colorText("Use 'w' to move up, 's' to move down, 'enter' to select, 'a' to add, 'd' to delete, 'q' to quit, or enter the index number.", "\033[1;33m")); // Yellow for instructions
        System.out.println(colorText("==================================================================\n", "\033[1;36m")); // Cyan for header
    }

    public void printFooter() {
        System.out.println(colorText("\n==================================================================", "\033[1;36m")); // Cyan for footer
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private String colorText(String text, String colorCode) {
        return colorCode + text + "\033[0m";
    }
}
