package com.itacademy.sigma_team;

import com.itacademy.sigma_team.cli_controller.CliController;
import com.itacademy.sigma_team.decorations.DecorationMapper;
import com.itacademy.sigma_team.decorations.use_cases.AddDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.DeleteDecorationUseCase;
import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Tree;
import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.dtos.TreeDTO;
import com.itacademy.sigma_team.flower_shop.FlowerShop;
import com.itacademy.sigma_team.flowers.FlowerMapper;
import com.itacademy.sigma_team.flowers.use_cases.AddFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.DeleteFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;
import com.itacademy.sigma_team.gateways.DecorationGateway;
import com.itacademy.sigma_team.gateways.TreeGateway;
import com.itacademy.sigma_team.print_stock.use_cases.PrintStockUseCase;
import com.itacademy.sigma_team.tickets.repositories.TicketDTO;
import com.itacademy.sigma_team.tickets.repositories.TicketMappers;
import com.itacademy.sigma_team.tickets.use_cases.AddTicketUseCase;
import com.itacademy.sigma_team.tickets.use_cases.DeleteTicketUseCase;
import com.itacademy.sigma_team.tickets.use_cases.TicketGateway;
import com.itacademy.sigma_team.trees.TreeMapper;
import com.itacademy.sigma_team.trees.use_cases.AddTreeUseCase;
import com.itacademy.sigma_team.trees.use_cases.DeleteTreeUseCase;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public final class App {/*
    public static void run() {


        FlowerGateway flowerGateway = new FlowerGateway() {
            @Override
            public void add(FlowerDTO dto) throws IOException {

            }

            @Override
            public void delete(FlowerDTO dto) throws IOException {

            }

            @Override
            public FlowerDTO get(String id) {
                return null;
            }

            @Override
            public Collection<FlowerDTO> getAll() {
                return null;
            }
        };

        TreeGateway treeGateway = new TreeGateway() {
            @Override
            public void addTree(TreeDTO treeDTO) {

            }

            @Override
            public TreeDTO getTree(String treeId) {
                return null;
            }

            @Override
            public void deleteTree(TreeDTO treeDTO) {

            }
        };

        DecorationGateway decorationGateway = new DecorationGateway() {
            @Override
            public DecorationDTO getDecoration(String decorationId) {
                return null;
            }

            @Override
            public void deleteDecoration(DecorationDTO decorationDTO) {

            }

            @Override
            public void addDecoration(DecorationDTO decorationDTO) {

            }
        };

        TicketGateway ticketGateway = new TicketGateway() {
            @Override
            public void add(TicketDTO dto) throws IOException {

            }

            @Override
            public void delete(TicketDTO dto) throws IOException {

            }

            @Override
            public TicketDTO get(String id) {
                return null;
            }

            @Override
            public Collection<TicketDTO> getAll() {
                return null;
            }
        };

        // Sample data
        Collection<Flower> flowers = Set.of();
        Collection<Tree> trees = Set.of();
        Collection<Decoration> decorations = Set.of();

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
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }),
                new DeleteFlowerUseCase(flower -> {
                    try {
                        flowerGateway.delete(FlowerMapper.toDto(flower));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }),
                new AddDecorationUseCase(decoration -> decorationGateway.addDecoration(DecorationMapper.toDto(decoration))),
                new DeleteDecorationUseCase(decoration -> decorationGateway.deleteDecoration(DecorationMapper.toDto(decoration))),
                new PrintStockUseCase(flowerShop),
                new AddTreeUseCase(tree -> treeGateway.addTree(TreeMapper.toDto(tree))),
                new AddTicketUseCase(ticket -> {
                    try {
                        ticketGateway.add(TicketMappers.toDto(ticket));
                    } catch (IOException e) {
                        throw new RuntimeException(e); // TODO we need to add a Logger here and also take care of checked exceptions in all other cases
                    }
                }),
                new DeleteTicketUseCase(ticket -> {
                    try {
                        ticketGateway.delete(TicketMappers.toDto(ticket));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }),
                new DeleteTreeUseCase(tree -> treeGateway.deleteTree(TreeMapper.toDto(tree)))
        );

        cliController.printStock();

    }*/
}