package com.itacademy.sigma_team.print_stock.use_cases;

import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Tree;
import com.itacademy.sigma_team.flower_shop.FlowerShop;

public final class PrintStockUseCase {

    private final FlowerShop flowerShop;

    public PrintStockUseCase(FlowerShop flowerShop) {
        this.flowerShop = flowerShop;
    }

    public void exec() {

        System.out.println("Welcome to " + flowerShop.name + "!\n");
        System.out.println("Here is our current stock:\n");

        System.out.println("Flowers:");
        for (Flower flower : flowerShop.flowers) {
            System.out.println(" - Color: " + flower.getColor() + ", Price: $" + flower.calculatePrice());
        }
        System.out.println();

        System.out.println("Trees:");
        for (Tree tree : flowerShop.trees) {
            System.out.println(" - Height: " + tree.height() + " meters");
        }
        System.out.println();

        System.out.println("Decorations:");
        for (Decoration decoration : flowerShop.decorations) {
            System.out.println(" - Material: " + decoration.material());
        }

    }

}
