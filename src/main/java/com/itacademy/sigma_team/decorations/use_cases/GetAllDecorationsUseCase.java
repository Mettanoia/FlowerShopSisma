package com.itacademy.sigma_team.decorations.use_cases;

import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Tree;

import java.util.Collection;
import java.util.function.Supplier;

public final class GetAllDecorationsUseCase {

    private final Supplier<Collection<Decoration>> getAllDecorationsMixin;

    public GetAllDecorationsUseCase(Supplier<Collection<Decoration>> getAllDecorationsMixin) {
        this.getAllDecorationsMixin = getAllDecorationsMixin;
    }


    public Collection<Decoration> exec() {
        return this.getAllDecorationsMixin.get();
    }

}
