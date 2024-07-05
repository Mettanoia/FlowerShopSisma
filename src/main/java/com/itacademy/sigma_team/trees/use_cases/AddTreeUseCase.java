package com.itacademy.sigma_team.trees.use_cases;


import com.itacademy.sigma_team.domain.Tree;

import java.util.function.Consumer;

public final class AddTreeUseCase {

    private final Consumer<Tree> addTreeMixin;

    public AddTreeUseCase(Consumer<Tree> addTreeMixin) {
        this.addTreeMixin = addTreeMixin;
    }

    public void exec(Tree tree) {
        this.addTreeMixin.accept(tree);
    }
}

