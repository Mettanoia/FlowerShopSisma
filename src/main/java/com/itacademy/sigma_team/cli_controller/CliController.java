package com.itacademy.sigma_team.cli_controller;

import com.itacademy.sigma_team.decorations.use_cases.AddDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.DeleteDecorationUseCase;
import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Ticket;
import com.itacademy.sigma_team.domain.Tree;
import com.itacademy.sigma_team.flower_shop.FlowerShop;
import com.itacademy.sigma_team.flowers.use_cases.AddFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.DeleteFlowerUseCase;
import com.itacademy.sigma_team.print_stock.use_cases.PrintStockUseCase;
import com.itacademy.sigma_team.tickets.use_cases.AddTicketUseCase;
import com.itacademy.sigma_team.tickets.use_cases.DeleteTicketUseCase;
import com.itacademy.sigma_team.trees.use_cases.AddTreeUseCase;

public final class CliController {

    private final AddFlowerUseCase addFlowerUseCase;
    private final DeleteFlowerUseCase deleteFlowerUseCase;
    private final AddDecorationUseCase addDecorationUseCase;
    private final DeleteDecorationUseCase deleteDecorationUseCase;
    private final PrintStockUseCase printStockUseCase;
    private final AddTreeUseCase addTreeUseCase;
    private final AddTicketUseCase addTicketUseCase;
    private final DeleteTicketUseCase deleteTicketUseCase;

    public CliController(AddFlowerUseCase addFlowerUseCase, DeleteFlowerUseCase deleteFlowerUseCase, AddDecorationUseCase addDecorationUseCase, DeleteDecorationUseCase deleteDecorationUseCase, PrintStockUseCase printStockUseCase, AddTreeUseCase addTreeUseCase, AddTicketUseCase addTicketUseCase, DeleteTicketUseCase deleteTicketUseCase) {
        this.addFlowerUseCase = addFlowerUseCase;
        this.deleteFlowerUseCase = deleteFlowerUseCase;
        this.addDecorationUseCase = addDecorationUseCase;
        this.deleteDecorationUseCase = deleteDecorationUseCase;
        this.printStockUseCase = printStockUseCase;
        this.addTreeUseCase = addTreeUseCase;
        this.addTicketUseCase = addTicketUseCase;
        this.deleteTicketUseCase = deleteTicketUseCase;
    }

    private void createFlowerShop(){}

    private void addFlower(Flower flower) {
        this.addFlowerUseCase.exec(flower);
    }

    private void addTree(Tree tree) { this.addTreeUseCase.exec(tree); }
    private void addDecoration(Decoration decoration) { this.addDecorationUseCase.exec(decoration); }
    private void addTicket(Ticket ticket) { this.addTicketUseCase.exec(ticket); }
    private void deleteFlower(Flower flower) { this.deleteFlowerUseCase.exec(flower); }
    private void deleteTree() {}
    private void deleteDecoration(Decoration decoration) { this.deleteDecorationUseCase.exec(decoration); }
    private void deleteTicket(Ticket ticket) { this.deleteTicketUseCase.exec(ticket); }
    public void printStock() { this.printStockUseCase.exec(); }
    private void printPurchaseHistory() {}
    private void printBenefits() {}

}
