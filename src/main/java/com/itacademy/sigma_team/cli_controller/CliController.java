package com.itacademy.sigma_team.cli_controller;

import com.itacademy.sigma_team.decorations.use_cases.AddDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.DeleteDecorationUseCase;
import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.flowers.use_cases.AddFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.DeleteFlowerUseCase;

public final class CliController {

    private final AddFlowerUseCase addFlowerUseCase;
    private final DeleteFlowerUseCase deleteFlowerUseCase;
    private final AddDecorationUseCase addDecorationUseCase;
    private final DeleteDecorationUseCase deleteDecorationUseCase;

    public CliController(AddFlowerUseCase addFlowerUseCase, DeleteFlowerUseCase deleteFlowerUseCase, AddDecorationUseCase addDecorationUseCase, DeleteDecorationUseCase deleteDecorationUseCase) {
        this.addFlowerUseCase = addFlowerUseCase;
        this.deleteFlowerUseCase = deleteFlowerUseCase;
        this.addDecorationUseCase = addDecorationUseCase;
        this.deleteDecorationUseCase = deleteDecorationUseCase;
    }

    private void createFlowerShop(){}

    private void addFlower(Flower flower) {
        this.addFlowerUseCase.exec(flower);
    }

    private void addTree() {}
    private void addDecoration(Decoration decoration) {this.addDecorationUseCase.exec(decoration);}
    private void addTicket() {}
    private void deleteFlower(Flower flower) {this.deleteFlowerUseCase.exec(flower);}
    private void deleteTree() {}
    private void deleteDecoration(Decoration decoration) {this.deleteDecorationUseCase.exec(decoration);}
    private void deleteTicket() {}
    private void printStock() {}
    private void printPurchaseHistory() {}
    private void printBenefits() {}

}
