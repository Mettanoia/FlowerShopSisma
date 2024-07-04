package com.itacademy.sigma_team.cli_controller;

import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.flowers.use_cases.AddFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.DeleteFlowerUseCase;

public final class CliController {

    private final AddFlowerUseCase addFlowerUseCase;
    private final DeleteFlowerUseCase deleteFlowerUseCase;

    public CliController(AddFlowerUseCase addFlowerUseCase, DeleteFlowerUseCase deleteFlowerUseCase) {
        this.addFlowerUseCase = addFlowerUseCase;
        this.deleteFlowerUseCase = deleteFlowerUseCase;
    }

    private void createFlowerShop(){}

    private void addFlower(Flower flower) {
        this.addFlowerUseCase.exec(flower);
    }

    private void addTree() {}
    private void addDecoration() {}
    private void addTicket() {}
    private void deleteFlower(Flower flower) {this.deleteFlowerUseCase.exec(flower);}
    private void deleteTree() {}
    private void deleteDecoration() {}
    private void deleteTicket() {}
    private void printStock() {}
    private void printPurchaseHistory() {}
    private void printBenefits() {}

}
