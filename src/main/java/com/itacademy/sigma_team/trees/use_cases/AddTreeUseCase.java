package com.itacademy.sigma_team.trees.use_cases;

import java.util.function.Consumer;

import com.itacademy.sigma_team.domain.Tree;

public class AddTreeUseCase {

    private final Consumer<Tree> onSuccess;

    public AddTreeUseCase(Consumer<Tree> onSuccess) {
        this.onSuccess = onSuccess;
    }

    public void exec(Tree tree) {
        onSuccess.accept(tree);
    }
}



