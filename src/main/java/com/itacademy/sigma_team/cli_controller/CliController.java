package com.itacademy.sigma_team.cli_controller;

import com.itacademy.sigma_team.decorations.use_cases.AddDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.DeleteDecorationUseCase;
import com.itacademy.sigma_team.domain.*;
import com.itacademy.sigma_team.flower_shop.FlowerShop;
import com.itacademy.sigma_team.flowers.use_cases.AddFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.DeleteFlowerUseCase;
import com.itacademy.sigma_team.print_stock.use_cases.PrintStockUseCase;
import com.itacademy.sigma_team.tickets.use_cases.AddTicketUseCase;
import com.itacademy.sigma_team.tickets.use_cases.DeleteTicketUseCase;
import com.itacademy.sigma_team.trees.use_cases.AddTreeUseCase;
import com.itacademy.sigma_team.trees.use_cases.DeleteTreeUseCase;

import java.util.List;

public final class CliController {

    private final AddFlowerUseCase addFlowerUseCase;
    private final DeleteFlowerUseCase deleteFlowerUseCase;
    private final AddDecorationUseCase addDecorationUseCase;
    private final DeleteDecorationUseCase deleteDecorationUseCase;
    private final PrintStockUseCase printStockUseCase;
    private final AddTreeUseCase addTreeUseCase;
    private final AddTicketUseCase addTicketUseCase;
    private final DeleteTicketUseCase deleteTicketUseCase;
    private final DeleteTreeUseCase deleteTreeUseCase;

    public CliController(AddFlowerUseCase addFlowerUseCase, DeleteFlowerUseCase deleteFlowerUseCase, AddDecorationUseCase addDecorationUseCase, DeleteDecorationUseCase deleteDecorationUseCase, PrintStockUseCase printStockUseCase, AddTreeUseCase addTreeUseCase, AddTicketUseCase addTicketUseCase, DeleteTicketUseCase deleteTicketUseCase, DeleteTreeUseCase deleteTreeUseCase) {
        this.addFlowerUseCase = addFlowerUseCase;
        this.deleteFlowerUseCase = deleteFlowerUseCase;
        this.addDecorationUseCase = addDecorationUseCase;
        this.deleteDecorationUseCase = deleteDecorationUseCase;
        this.printStockUseCase = printStockUseCase;
        this.addTreeUseCase = addTreeUseCase;
        this.addTicketUseCase = addTicketUseCase;
        this.deleteTicketUseCase = deleteTicketUseCase;
        this.deleteTreeUseCase = deleteTreeUseCase;
    }

    public void createFlowerShop(FlowerShop flowerShop) {
        // Implementar lógica para crear una floristeria
    }

    public void addFlower(Flower flower) {
        this.addFlowerUseCase.exec(flower);
    }

    public void addTree(Tree tree) {
        this.addTreeUseCase.exec(tree);
    }

    public void addDecoration(Decoration decoration) {
        this.addDecorationUseCase.exec(decoration);
    }

    public void addTicket(List<TicketItem> ticket) {
        this.addTicketUseCase.exec(ticket);
    }

    public void deleteFlower(Flower flower) {
        this.deleteFlowerUseCase.exec(flower);
    }

    public void deleteTree(Tree tree) {
        this.deleteTreeUseCase.exec(tree);
    }

    public void deleteDecoration(Decoration decoration) {
        this.deleteDecorationUseCase.exec(decoration);
    }

    public void deleteTicket(Ticket ticket) {
        this.deleteTicketUseCase.exec(ticket);
    }

    public void printStock() {
        this.printStockUseCase.exec();
    }

    public void printPurchaseHistory() {
        // Implementar lógica para imprimir el historial de compras
    }

    public void printBenefits() {
        // Implementar lógica para imprimir el valor total de la floristeria
    }

    public List<Flower> listFlowers() {
        // Implementar lógica para listar flores
        return List.of(); // Placeholder
    }

    public List<Tree> listTrees() {
        // Implementar lógica para listar árboles
        return List.of(); // Placeholder
    }

    public List<Decoration> listDecorations() {
        // Implementar lógica para listar decoraciones
        return List.of(); // Placeholder
    }
}



