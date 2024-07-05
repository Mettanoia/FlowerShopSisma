package com.itacademy.sigma_team.trees.use_cases;


import com.itacademy.sigma_team.domain.Tree;

import java.util.function.Consumer;

public final class DeleteTreeUseCase {

    private final Consumer<Tree> deleteTreeMixin;

    public DeleteTreeUseCase(Consumer<Tree> deleteTreeMixin) {
        this.deleteTreeMixin = deleteTreeMixin;
    }

    public void exec(Tree tree) {
        this.deleteTreeMixin.accept(tree);
    }
}

