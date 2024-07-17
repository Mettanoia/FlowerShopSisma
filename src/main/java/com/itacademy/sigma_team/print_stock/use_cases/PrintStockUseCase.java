package com.itacademy.sigma_team.print_stock.use_cases;

import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Tree;
import com.itacademy.sigma_team.flower_shop.FlowerShop;

public final class PrintStockUseCase {

    public void exec(FlowerShop flowerShop) {

        System.out.println("Welcome to " + flowerShop.name + "!\n");
        System.out.println("Here is our current stock:\n");

        System.out.println("Flowers:");
        for (Flower flower : flowerShop.flowers) {
            System.out.println(" - Name: " + flower.getName() + ", Color: " + flower.getColor() +
                    ", Price: €" + flower.calculatePrice() + ", Stock: " + flower.getStock());
        }
        System.out.println();

        System.out.println("Trees:");
        for (Tree tree : flowerShop.trees) {
            System.out.println(" - Name: " + tree.getName() + ", Height: " + tree.getHeight() +
                    " meters, Price: €" + tree.calculatePrice() + ", Stock: " + tree.getStock());
        }
        System.out.println();

        System.out.println("Decorations:");
        for (Decoration decoration : flowerShop.decorations) {
            System.out.println(" - Name: " + decoration.getName() + ", Material: " + decoration.getMaterial() +
                    ", Price: €" + decoration.calculatePrice() + ", Stock: " + decoration.getStock());
        }

    }

}
