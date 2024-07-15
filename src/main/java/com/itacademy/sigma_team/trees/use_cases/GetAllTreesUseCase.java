package com.itacademy.sigma_team.trees.use_cases;

import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Tree;

import java.util.Collection;
import java.util.function.Supplier;

public final class GetAllTreesUseCase {

    private final Supplier<Collection<Tree>> getAllTreesMixin;

    public GetAllTreesUseCase(Supplier<Collection<Tree>> getAllTreesMixin) {
        this.getAllTreesMixin = getAllTreesMixin;
    }

    public Collection<Tree> exec() {
        return this.getAllTreesMixin.get();
    }

}
