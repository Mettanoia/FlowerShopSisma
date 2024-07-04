package com.itacademy.sigma_team.decorations.use_cases;

import com.itacademy.sigma_team.domain.Decoration;

import java.util.function.Consumer;

public final class AddDecorationUseCase {

    private final Consumer<Decoration> addDecorationMixin;

    public AddDecorationUseCase(Consumer<Decoration> addDecorationMixin) {
        this.addDecorationMixin = addDecorationMixin;
    }

    public void exec(Decoration decoration) {
        this.addDecorationMixin.accept(decoration);
    }

}
