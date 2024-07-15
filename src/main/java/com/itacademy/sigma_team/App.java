package com.itacademy.sigma_team;

import com.itacademy.sigma_team.cli_controller.CliController;
import com.itacademy.sigma_team.decorations.repositories.DecorationMapper;
import com.itacademy.sigma_team.decorations.use_cases.AddDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.DeleteDecorationUseCase;
import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Tree;
import com.itacademy.sigma_team.flower_shop.FlowerShop;
import com.itacademy.sigma_team.flowers.repositories.FlowerMapper;
import com.itacademy.sigma_team.flowers.use_cases.AddFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.DeleteFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;
import com.itacademy.sigma_team.decorations.use_cases.DecorationGateway;
import com.itacademy.sigma_team.flowers.use_cases.GetAllFlowersUseCase;
import com.itacademy.sigma_team.repositories_factories.DecorationRepositoryFactory;
import com.itacademy.sigma_team.repositories_factories.TreeRepositoryFactory;
import com.itacademy.sigma_team.trees.use_cases.GetAllTreesUseCase;
import com.itacademy.sigma_team.trees.use_cases.TreeGateway;
import com.itacademy.sigma_team.print_stock.use_cases.PrintStockUseCase;
import com.itacademy.sigma_team.repositories_factories.FlowerRepositoryFactory;
import com.itacademy.sigma_team.tickets.repositories.TicketMappers;
import com.itacademy.sigma_team.tickets.repositories.TicketSqlRepository;
import com.itacademy.sigma_team.tickets.use_cases.AddTicketUseCase;
import com.itacademy.sigma_team.tickets.use_cases.DeleteTicketUseCase;
import com.itacademy.sigma_team.tickets.use_cases.GetTicketUseCase;
import com.itacademy.sigma_team.tickets.use_cases.TicketGateway;
import com.itacademy.sigma_team.repositories_factories.TicketRepositoryFactory;
import com.itacademy.sigma_team.trees.repositories.TreeMapper;
import com.itacademy.sigma_team.trees.use_cases.AddTreeUseCase;
import com.itacademy.sigma_team.trees.use_cases.DeleteTreeUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public final class App {

    private static final Logger logger = LoggerFactory.getLogger(TicketSqlRepository.class);

    public static void run() {

        // Repositories to inject in the use cases
        FlowerGateway flowerGateway = FlowerRepositoryFactory.getRepository();
        TicketGateway ticketGateway = TicketRepositoryFactory.getInstance();
        TreeGateway treeGateway = TreeRepositoryFactory.getInstance();
        DecorationGateway decorationGateway = DecorationRepositoryFactory.getInstance();

        // Sample data
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

        } catch (IOException e) {logException();}

        // Building the FlowerShop
        FlowerShop flowerShop = new FlowerShop.FlowerShopBuilder("Sigma Flower Shop")
                .flowers(flowers)
                .trees(trees)
                .decorations(decorations)
                .build();

        CliController cliController = new CliController(

                new AddFlowerUseCase(flower -> {
                    try {
                        flowerGateway.add(FlowerMapper.toDto(flower));
                    } catch (IOException e) {logException();}
                }),

                new DeleteFlowerUseCase(flower -> {
                    try {
                        flowerGateway.delete(FlowerMapper.toDto(flower));
                    } catch (IOException e) {logException();}
                }),

                new GetAllFlowersUseCase(() -> {
                    try {
                        return flowerGateway.getAll().stream().map(FlowerMapper::toDomain).collect(Collectors.toSet());
                    } catch (IOException e) {logException();}
                    return Set.of();
                }),

                new AddDecorationUseCase(decoration -> {
                    try {
                        decorationGateway.add(DecorationMapper.toDto(decoration));
                    } catch (IOException e) {logException();}
                }),

                new DeleteDecorationUseCase(decoration -> {
                    try {
                        decorationGateway.delete(DecorationMapper.toDto(decoration));
                    } catch (IOException e) {logException();}
                }),

                new PrintStockUseCase(createFlowerShop("Our shop", flowerGateway, treeGateway, decorationGateway)),

                new AddTreeUseCase(tree -> {
                    try {
                        treeGateway.add(TreeMapper.toDto(tree));
                    } catch (IOException e) {logException();}
                }),

                new GetAllTreesUseCase(() -> {
                    try {
                        return treeGateway.getAll().stream().map(TreeMapper::toDomain).collect(Collectors.toSet());
                    } catch (IOException e) {
                        logException();
                        return null;
                    }
                }),

                new AddTicketUseCase(ticket -> {
                    try {
                        ticketGateway.add(TicketMappers.toDto(ticket));
                    } catch (IOException e) {logException();}
                }),

                new DeleteTicketUseCase(ticket -> {
                    try {
                        ticketGateway.delete(TicketMappers.toDto(ticket));
                    } catch (IOException e) {logException();}
                }),

                new DeleteTreeUseCase(tree -> {
                    try {
                        treeGateway.delete(TreeMapper.toDto(tree));
                    } catch (IOException e) {logException();}
                }),

                new GetTicketUseCase(ticketId -> {
                    try {
                        return TicketMappers.toDomain(ticketGateway.get(ticketId));
                    } catch (IOException e) {
                        logException();
                        return null;
                    }
                })

        );

        cliController.printStock();

    }

    public static FlowerShop createFlowerShop(String flowerShopName, FlowerGateway flowerGateway, TreeGateway treeGateway, DecorationGateway decorationGateway) {

        try {
            return new FlowerShop.FlowerShopBuilder(flowerShopName)
                    .trees(
                            treeGateway.getAll().stream()
                                    .map(TreeMapper::toDomain)
                                    .collect(Collectors.toSet())
                    )

                    .flowers(
                            flowerGateway.getAll().stream()
                                    .map(FlowerMapper::toDomain)
                                    .collect(Collectors.toSet())
                    )

                    .decorations(
                            decorationGateway.getAll().stream()
                                    .map(DecorationMapper::toDomain)
                                    .collect(Collectors.toSet())
                    )

                    .build();

        } catch (IOException e) {
            logException();
            return null;
        }

    }


    private static void logException() {
        logger.error("Exception thrown while calling the repository.");
    }

}
