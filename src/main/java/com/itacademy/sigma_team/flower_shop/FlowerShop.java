package com.itacademy.sigma_team.flower_shop;

import com.itacademy.sigma_team.abstract_shop.ShopAbstract;
import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Tree;

import java.util.Collection;
import java.util.Set;

public final class FlowerShop extends ShopAbstract {

    public final String name;
    public final Collection<Flower> flowers;
    public final Collection<Tree> trees;
    public final Collection<Decoration> decorations;

    public FlowerShop(FlowerShopBuilder builder){
        this.name = builder.flowerShopName;
        this.flowers = builder.flowers;
        this.trees = builder.trees;
        this.decorations = builder.decorations;
    }

    static public final class FlowerShopBuilder extends Builder<FlowerShopBuilder> {

        private final String flowerShopName;
        private Collection<Flower> flowers = Set.of();
        private Collection<Tree> trees = Set.of();
        private Collection<Decoration> decorations = Set.of();

        public FlowerShopBuilder(String flowerShopName) {
            this.flowerShopName = flowerShopName;
        }

        public FlowerShopBuilder flowers(Collection<Flower> flowers) {
            this.flowers = Set.copyOf(flowers);
            return self();
        }

        public FlowerShopBuilder trees(Collection<Tree> trees) {
            this.trees = Set.copyOf(trees);
            return self();
        }

        public FlowerShopBuilder decorations(Collection<Decoration> decorations) {
            this.decorations = Set.copyOf(decorations);
            return self();
        }

        @Override
        protected FlowerShopBuilder self() {
            return this;
        }

        @Override
        public FlowerShop build() {
            return new FlowerShop(this);
        }

    }

}
