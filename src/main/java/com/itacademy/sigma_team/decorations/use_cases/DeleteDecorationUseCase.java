package com.itacademy.sigma_team.decorations.use_cases;

import com.itacademy.sigma_team.domain.Decoration;

import java.util.function.Consumer;

public final class DeleteDecorationUseCase {

    private final Consumer<Decoration> deleteDecorationMixin;

    public DeleteDecorationUseCase(Consumer<Decoration> deleteDecorationMixin) {
        this.deleteDecorationMixin = deleteDecorationMixin;
    }

    public void exec(Decoration decoration) {
        this.deleteDecorationMixin.accept(decoration);
    }

}
