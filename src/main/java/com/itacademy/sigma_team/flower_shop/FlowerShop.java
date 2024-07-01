package com.itacademy.sigma_team.flower_shop;

import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Tree;

import java.util.Collection;

public final class FlowerShop {

    private final Collection<Flower> flowers;
    private final Collection<Tree> trees;
    private final Collection<Decoration> decorations;

    private FlowerShop(Collection<Flower> flowers, Collection<Tree> trees, Collection<Decoration> decorations){
        this.flowers = flowers;
        this.trees = trees;
        this.decorations = decorations;
    }

}
