package com.itacademy.sigma_team;

import com.itacademy.sigma_team.cli_controller.CliController;
import com.itacademy.sigma_team.decorations.repositories.DecorationMapper;
import com.itacademy.sigma_team.decorations.use_cases.AddDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.DeleteDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.GetAllDecorationsUseCase;
import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Tree;
import com.itacademy.sigma_team.flower_shop.FlowerShop;
import com.itacademy.sigma_team.flower_shop.use_cases.UpdateFlowerShopUseCase;
import com.itacademy.sigma_team.flowers.repositories.FlowerMapper;
import com.itacademy.sigma_team.flowers.use_cases.AddFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.DeleteFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;
import com.itacademy.sigma_team.decorations.use_cases.DecorationGateway;
import com.itacademy.sigma_team.flowers.use_cases.GetAllFlowersUseCase;
import com.itacademy.sigma_team.repositories_factories.DecorationRepositoryFactory;
import com.itacademy.sigma_team.repositories_factories.TreeRepositoryFactory;
import com.itacademy.sigma_team.tickets.use_cases.*;
import com.itacademy.sigma_team.trees.use_cases.GetAllTreesUseCase;
import com.itacademy.sigma_team.trees.use_cases.TreeGateway;
import com.itacademy.sigma_team.print_stock.use_cases.PrintStockUseCase;
import com.itacademy.sigma_team.repositories_factories.FlowerRepositoryFactory;
import com.itacademy.sigma_team.tickets.repositories.TicketMappers;
import com.itacademy.sigma_team.repositories_factories.TicketRepositoryFactory;
import com.itacademy.sigma_team.trees.repositories.TreeMapper;
import com.itacademy.sigma_team.trees.use_cases.AddTreeUseCase;
import com.itacademy.sigma_team.trees.use_cases.DeleteTreeUseCase;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public final class App {

    public static void run() {

        // Repositories to inject in the use cases
        FlowerGateway flowerGateway = FlowerRepositoryFactory.getRepository();
        TicketGateway ticketGateway = TicketRepositoryFactory.getInstance();
        TreeGateway treeGateway = TreeRepositoryFactory.getInstance();
        DecorationGateway decorationGateway = DecorationRepositoryFactory.getInstance();


        CliController cliController = new CliController(

                new AddFlowerUseCase(flower -> {
                    try {
                        flowerGateway.add(FlowerMapper.toDto(flower));
                    } catch (IOException e) {logException(AddFlowerUseCase.class);}
                }),

                new DeleteFlowerUseCase(flower -> {
                    try {
                        flowerGateway.delete(FlowerMapper.toDto(flower));
                    } catch (IOException e) {logException(DeleteFlowerUseCase.class);}
                }),

                new GetAllFlowersUseCase(() -> {
                    try {
                        return flowerGateway.getAll().stream().map(FlowerMapper::toDomain).collect(Collectors.toSet());
                    } catch (IOException e) {logException(GetAllFlowersUseCase.class);}
                    return Set.of();
                }),

                new AddDecorationUseCase(decoration -> {
                    try {
                        decorationGateway.add(DecorationMapper.toDto(decoration));
                    } catch (IOException e) {logException(AddDecorationUseCase.class);}
                }),

                new DeleteDecorationUseCase(decoration -> {
                    try {
                        decorationGateway.delete(DecorationMapper.toDto(decoration));
                    } catch (IOException e) {logException(DeleteDecorationUseCase.class);}
                }),

                new GetAllDecorationsUseCase(() -> {
                    try {
                        return decorationGateway.getAll().stream().map(DecorationMapper::toDomain).collect(Collectors.toSet());
                    } catch (IOException e) {
                        logException(GetAllDecorationsUseCase.class);
                        return null;
                    }
                }),

                new PrintStockUseCase(),

                new AddTreeUseCase(tree -> {
                    try {
                        treeGateway.add(TreeMapper.toDto(tree));
                    } catch (IOException e) {logException(AddTreeUseCase.class);}
                }),

                new GetAllTreesUseCase(() -> {
                    try {
                        return treeGateway.getAll().stream().map(TreeMapper::toDomain).collect(Collectors.toSet());
                    } catch (IOException e) {
                        logException(GetAllTreesUseCase.class);
                        return null;
                    }
                }),

                new AddTicketUseCase(ticket -> {
                    try {
                        ticketGateway.add(TicketMappers.toDto(ticket));
                    } catch (IOException e) {logException(AddTicketUseCase.class);}
                }),

                new DeleteTicketUseCase(ticket -> {
                    try {
                        ticketGateway.delete(TicketMappers.toDto(ticket));
                    } catch (IOException e) {logException(DeleteTicketUseCase.class);}
                }),

                new DeleteTreeUseCase(tree -> {
                    try {
                        treeGateway.delete(TreeMapper.toDto(tree));
                    } catch (IOException e) {logException(DeleteTreeUseCase.class);}
                }),

                new GetTicketUseCase(ticketId -> {
                    try {
                        return TicketMappers.toDomain(ticketGateway.get(ticketId));
                    } catch (IOException e) {
                        logException(GetTicketUseCase.class);
                        return null;
                    }
                }),

                new GetAllTicketsUseCase(() -> {
                    try {
                        return ticketGateway.getAll().stream().map(TicketMappers::toDomain).collect(Collectors.toSet());
                    } catch (IOException e) {
                        logException(GetAllTicketsUseCase.class);
                        return null;
                    }
                }),

                new UpdateFlowerShopUseCase(flowerGateway, treeGateway, decorationGateway),

                new Scanner(System.in),

                getFlowerShop(flowerGateway, treeGateway, decorationGateway)

        );

        cliController.displayMenu();

    }

    private static FlowerShop getFlowerShop(FlowerGateway flowerGateway, TreeGateway treeGateway, DecorationGateway decorationGateway) {

        Collection<Flower> flowers = null;
        Collection<Tree> trees = null;
        Collection<Decoration> decorations = null;

        try {

            flowers = flowerGateway.getAll().stream()
                    .map(FlowerMapper::toDomain)
                    .collect(Collectors.toSet());

            trees = treeGateway.getAll().stream()
                    .map(TreeMapper::toDomain)
                    .collect(Collectors.toSet());

            decorations = decorationGateway.getAll().stream()
                    .map(DecorationMapper::toDomain)
                    .collect(Collectors.toSet());

        } catch (IOException e) {logException(AddFlowerUseCase.class);}

        // Building the FlowerShop
        return new FlowerShop.FlowerShopBuilder("Sigma Flower Shop")
                .flowers(flowers)
                .trees(trees)
                .decorations(decorations)
                .build();
    }

    private static <T> void logException(Class<T> className) {
        LoggerFactory.getLogger(className).error("Exception thrown while calling the repository.");
    }

}
