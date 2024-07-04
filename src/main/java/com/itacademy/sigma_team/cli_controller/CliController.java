package com.itacademy.sigma_team.cli_controller;

import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.flowers.use_cases.AddFlowerUseCase;

public final class CliController {

    private final AddFlowerUseCase addFlowerUseCase;

    public CliController(AddFlowerUseCase addFlowerUseCase) {
        this.addFlowerUseCase = addFlowerUseCase;
    }

    private void createFlowerShop(){}

    private void addFlower(Flower flower) {
        this.addFlowerUseCase.exec(flower);
    }

    private void addTree() {}
    private void addDecoration() {}
    private void addTicket() {}
    private void deleteFlower() {}
    private void deleteTree() {}
    private void deleteDecoration() {}
    private void deleteTicket() {}
    private void printStock() {}
    private void printPurchaseHistory() {}
    private void printBenefits() {}

}
