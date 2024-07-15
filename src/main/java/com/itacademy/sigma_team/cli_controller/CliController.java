package com.itacademy.sigma_team.cli_controller;

import com.itacademy.sigma_team.decorations.use_cases.AddDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.DeleteDecorationUseCase;
import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Ticket;
import com.itacademy.sigma_team.domain.Tree;
import com.itacademy.sigma_team.flowers.use_cases.AddFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.DeleteFlowerUseCase;
import com.itacademy.sigma_team.print_stock.use_cases.PrintStockUseCase;
import com.itacademy.sigma_team.tickets.use_cases.AddTicketUseCase;
import com.itacademy.sigma_team.tickets.use_cases.DeleteTicketUseCase;
import com.itacademy.sigma_team.tickets.use_cases.GetTicketUseCase;
import com.itacademy.sigma_team.trees.use_cases.AddTreeUseCase;
import com.itacademy.sigma_team.trees.use_cases.DeleteTreeUseCase;

public final class CliController {

    //Flowers use cases
    private final AddFlowerUseCase addFlowerUseCase;
    private final DeleteFlowerUseCase deleteFlowerUseCase;

    // Decoration use cases
    private final AddDecorationUseCase addDecorationUseCase;
    private final DeleteDecorationUseCase deleteDecorationUseCase;

    // Printing use cases
    private final PrintStockUseCase printStockUseCase;

    // Tree use cases
    private final AddTreeUseCase addTreeUseCase;
    private final DeleteTreeUseCase deleteTreeUseCase;


    // Ticket use cases
    private final AddTicketUseCase addTicketUseCase;
    private final DeleteTicketUseCase deleteTicketUseCase;
    private final GetTicketUseCase getTicketUseCase;


    public CliController(AddFlowerUseCase addFlowerUseCase, DeleteFlowerUseCase deleteFlowerUseCase, AddDecorationUseCase addDecorationUseCase, DeleteDecorationUseCase deleteDecorationUseCase, PrintStockUseCase printStockUseCase, AddTreeUseCase addTreeUseCase, AddTicketUseCase addTicketUseCase, DeleteTicketUseCase deleteTicketUseCase, DeleteTreeUseCase deleteTreeUseCase, GetTicketUseCase getTicketUseCase) {
        this.addFlowerUseCase = addFlowerUseCase;
        this.deleteFlowerUseCase = deleteFlowerUseCase;
        this.addDecorationUseCase = addDecorationUseCase;
        this.deleteDecorationUseCase = deleteDecorationUseCase;
        this.printStockUseCase = printStockUseCase;
        this.addTreeUseCase = addTreeUseCase;
        this.addTicketUseCase = addTicketUseCase;
        this.deleteTicketUseCase = deleteTicketUseCase;
        this.deleteTreeUseCase = deleteTreeUseCase;
        this.getTicketUseCase = getTicketUseCase;
    }

    // Flower show entrypoint
    private void createFlowerShop(){}

    // Flowers entry points
    private void addFlower(Flower flower) {
        this.addFlowerUseCase.exec(flower);
    }
    private void deleteFlower(Flower flower) { this.deleteFlowerUseCase.exec(flower); }

    // Trees entry points
    private void addTree(Tree tree) { this.addTreeUseCase.exec(tree); }
    private void deleteTree(Tree tree) { this.deleteTreeUseCase.exec(tree); }

    // Decoration entry points
    private void addDecoration(Decoration decoration) { this.addDecorationUseCase.exec(decoration); }


    // Ticket entry points
    private void addTicket(Ticket ticket) { this.addTicketUseCase.exec(ticket); }
    private void deleteTicket(Ticket ticket) { this.deleteTicketUseCase.exec(ticket); }

    // Printing entry points
    public void printStock() { this.printStockUseCase.exec(); }
    private void printPurchaseHistory() {}
    private void printBenefits() {}

}
