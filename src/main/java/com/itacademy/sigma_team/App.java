package com.itacademy.sigma_team;

import com.itacademy.sigma_team.common.Adder;
import com.itacademy.sigma_team.decorations.repositories.DecorationMapper;
import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Ticket;
import com.itacademy.sigma_team.domain.Tree;
import com.itacademy.sigma_team.flower_shop.repositories.ShopRepository;
import com.itacademy.sigma_team.flower_shop.use_cases.FlowerShopGateway;
import com.itacademy.sigma_team.flowers.repositories.FlowerMapper;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;
import com.itacademy.sigma_team.decorations.use_cases.DecorationGateway;
import com.itacademy.sigma_team.repositories_factories.*;
import com.itacademy.sigma_team.tickets.use_cases.*;
import com.itacademy.sigma_team.trees.use_cases.TreeGateway;
import com.itacademy.sigma_team.tickets.repositories.TicketMappers;
import com.itacademy.sigma_team.trees.repositories.TreeMapper;
import com.itacademy.sigma_team.ui.CrudControllerAdapter;
import com.itacademy.sigma_team.ui.menus.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class App {

    public static void run() {

        // Repositories to inject in the use cases
        FlowerGateway flowerGateway = FlowerRepositoryFactory.getRepository();
        TicketGateway ticketGateway = TicketRepositoryFactory.getInstance();
        TreeGateway treeGateway = TreeRepositoryFactory.getInstance();
        DecorationGateway decorationGateway = DecorationRepositoryFactory.getInstance();
        FlowerShopGateway flowerShopGateway = FlowerShopRepositoryFactory.getInstance();


        // Flower CRUD Controller
        FlowersUseCases flowersUseCases = getFlowersUseCases(flowerGateway);
        CrudControllerAdapter<Flower, String> flowerController =
                CrudControllerAdapter.<Flower, String>builder()
                        .addMixin(flowersUseCases.addFlowerUseCase())
                        .deleteMixin(flowersUseCases.deleteFlowerUseCase())
                        .getByIdMixin(flowersUseCases.getFlowerByIdMixin())
                        .getAllMixin(flowersUseCases.getAllFlowersMixin())
                        .build();


        // Ticket CRUD Controller
        TicketUseCases ticketUseCases = getTicketUseCases(ticketGateway);
        CrudControllerAdapter<Ticket, String> ticketController = CrudControllerAdapter.<Ticket, String>builder()
                .addMixin(ticketUseCases.addTicketUseCase())
                .deleteMixin(ticketUseCases.deleteTicketUseCase())
                .getByIdMixin(ticketUseCases.getTicketByIdMixin())
                .getAllMixin(ticketUseCases.getAllTicketsMixin())
                .build();


        // Tree CRUD Controller
        TreeUseCases treeUseCases = getTreeUseCases(treeGateway);
        CrudControllerAdapter<Tree, String> treeController = CrudControllerAdapter.<Tree, String>builder()
                .addMixin(treeUseCases.addTreeUseCase())
                .deleteMixin(treeUseCases.deleteTreeUseCase())
                .getByIdMixin(treeUseCases.getTreeByIdMixin())
                .getAllMixin(treeUseCases.getAllTreesMixin())
                .build();


        // Decoration CRUD Controller
        DecorationUseCases decorationUseCases = getDecorationUseCases(decorationGateway);
        CrudControllerAdapter<Decoration, String> decorationController = CrudControllerAdapter.<Decoration, String>builder()
                .addMixin(decorationUseCases.addDecorationUseCase())
                .deleteMixin(decorationUseCases.deleteDecorationUseCase())
                .getByIdMixin(decorationUseCases.getDecorationByIdMixin())
                .getAllMixin(decorationUseCases.getAllDecorationsMixin())
                .build();


        ShopRepository shopRepository = new ShopRepository();

        List<String> shopNames = shopRepository.getAllShopNames();

        new ShopMenu(
                new FlowerMenu(flowerController),
                new TreeMenu(treeController),
                new DecorationMenu(decorationController),
                new TicketMenu(ticketController, decorationController, treeController, flowerController),
                shopRepository,
                shopNames
        ).showShopMenu();

    }


    // Helper methods to get the use cases for every feature

    private static FlowersUseCases getFlowersUseCases(FlowerGateway flowerGateway) {

        Consumer<Flower> deleteFlowerUseCase =
                flower -> {
                    try {
                        flowerGateway.delete(FlowerMapper.toDto(flower));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        Adder<Flower> addFlowerUseCase = flower -> {
            try {
                flowerGateway.add(FlowerMapper.toDto(flower));
                return Optional.empty();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        Function<String, Optional<Flower>> getFlowerByIdMixin =
                s -> {
                    try {
                        return Optional.of(FlowerMapper.toDomain(flowerGateway.get(s)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        Supplier<Collection<Flower>> getAllFlowersMixin =
                () -> {
                    try {
                        return flowerGateway.getAll().stream().map(FlowerMapper::toDomain).toList();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        return new FlowersUseCases(deleteFlowerUseCase, addFlowerUseCase, getFlowerByIdMixin, getAllFlowersMixin);

    }
    private static TicketUseCases getTicketUseCases(TicketGateway ticketGateway) {

        Consumer<Ticket> deleteTicketUseCase =
                ticket -> {
                    try {
                        ticketGateway.delete(TicketMappers.toDto(ticket));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        Adder<Ticket> addTicketUseCase =
                ticket -> {
                    try {
                        ticketGateway.add(TicketMappers.toDto(ticket));
                        return Optional.empty();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        Function<String, Optional<Ticket>> getTicketByIdMixin =
                s -> {
                    try {
                        return Optional.of(TicketMappers.toDomain(ticketGateway.get(s)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        Supplier<Collection<Ticket>> getAllTicketsMixin =
                () -> {
                    try {
                        return ticketGateway.getAll().stream().map(TicketMappers::toDomain).toList();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        return new TicketUseCases(deleteTicketUseCase, addTicketUseCase, getTicketByIdMixin, getAllTicketsMixin);
    }
    private static TreeUseCases getTreeUseCases(TreeGateway treeGateway) {

        Consumer<Tree> deleteTreeUseCase =
                tree -> {
                    try {
                        treeGateway.delete(TreeMapper.toDto(tree));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        Adder<Tree> addTreeUseCase =
                tree -> {
                    try {
                        treeGateway.add(TreeMapper.toDto(tree));
                        return Optional.empty();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        Function<String, Optional<Tree>> getTreeByIdMixin =
                s -> {
                    try {
                        return Optional.of(TreeMapper.toDomain(treeGateway.get(s)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        Supplier<Collection<Tree>> getAllTreesMixin =
                () -> {
                    try {
                        return treeGateway.getAll().stream().map(TreeMapper::toDomain).toList();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        return new TreeUseCases(deleteTreeUseCase, addTreeUseCase, getTreeByIdMixin, getAllTreesMixin);
    }
    private static DecorationUseCases getDecorationUseCases(DecorationGateway decorationGateway) {

        Consumer<Decoration> deleteDecorationUseCase =
                decoration -> {
                    try {
                        decorationGateway.delete(DecorationMapper.toDto(decoration));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        Adder<Decoration> addDecorationUseCase =
                decoration -> {
                    try {
                        decorationGateway.add(DecorationMapper.toDto(decoration));
                        return Optional.empty();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        Function<String, Optional<Decoration>> getDecorationByIdMixin =
                s -> {
                    try {
                        return Optional.of(DecorationMapper.toDomain(decorationGateway.get(s)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        Supplier<Collection<Decoration>> getAllDecorationsMixin =
                () -> {
                    try {
                        return decorationGateway.getAll().stream().map(DecorationMapper::toDomain).toList();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };

        return new DecorationUseCases(deleteDecorationUseCase, addDecorationUseCase, getDecorationByIdMixin, getAllDecorationsMixin);
    }


    // Records to create bundles of use cases to improve readability

    private record DecorationUseCases(Consumer<Decoration> deleteDecorationUseCase, Adder<Decoration> addDecorationUseCase, Function<String, Optional<Decoration>> getDecorationByIdMixin, Supplier<Collection<Decoration>> getAllDecorationsMixin) { }
    private record FlowersUseCases(Consumer<Flower> deleteFlowerUseCase, Adder<Flower> addFlowerUseCase, Function<String, Optional<Flower>> getFlowerByIdMixin, Supplier<Collection<Flower>> getAllFlowersMixin) { }
    private record TicketUseCases(Consumer<Ticket> deleteTicketUseCase, Adder<Ticket> addTicketUseCase, Function<String, Optional<Ticket>> getTicketByIdMixin, Supplier<Collection<Ticket>> getAllTicketsMixin) { }
    private record TreeUseCases(Consumer<Tree> deleteTreeUseCase, Adder<Tree> addTreeUseCase, Function<String, Optional<Tree>> getTreeByIdMixin, Supplier<Collection<Tree>> getAllTreesMixin) { }


}
