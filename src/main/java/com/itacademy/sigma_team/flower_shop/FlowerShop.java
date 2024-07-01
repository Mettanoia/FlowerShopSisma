package com.itacademy.sigma_team.flower_shop;

import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Tree;

import java.util.Collection;
import java.util.Set;

public final class FlowerShop {

    private final String name;
    private final Collection<Flower> flowers;
    private final Collection<Tree> trees;
    private final Collection<Decoration> decorations;

    private FlowerShop(String name, Collection<Flower> flowers, Collection<Tree> trees, Collection<Decoration> decorations){
        this.name = name;
        this.flowers = flowers;
        this.trees = trees;
        this.decorations = decorations;
    }

    static final class FlowerShopBuilder {

        private final String flowerShopName;
        private Collection<Flower> flowers;
        private Collection<Tree> trees;
        private Collection<Decoration> decorations;

        FlowerShopBuilder(String name) {
            this.flowerShopName = name;
            this.flowers = Set.of();
            this.trees = Set.of();
            this.decorations = Set.of();
        }

        FlowerShopBuilder flowers(Collection<Flower> flowers) {
            this.flowers = Set.copyOf(flowers);
            return this;
        }

        FlowerShopBuilder trees(Collection<Tree> trees) {
            this.trees = Set.copyOf(trees);
            return this;
        }

        FlowerShopBuilder decorations(Collection<Decoration> decorations) {
            this.decorations = Set.copyOf(decorations);
            return this;
        }

        FlowerShop build() {
            return new FlowerShop(
                    this.flowerShopName,
                    this.flowers,
                    this.trees,
                    this.decorations
            );
        }

    }

}
