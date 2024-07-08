package com.itacademy.sigma_team;

import com.itacademy.sigma_team.cli_controller.CliController;
import com.itacademy.sigma_team.decorations.DecorationMapper;
import com.itacademy.sigma_team.decorations.repositories.DecorationDTO;
import com.itacademy.sigma_team.decorations.use_cases.AddDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.DeleteDecorationUseCase;
import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.domain.Material;
import com.itacademy.sigma_team.domain.Tree;
import com.itacademy.sigma_team.flower_shop.FlowerShop;
import com.itacademy.sigma_team.flowers.FlowerMapper;
import com.itacademy.sigma_team.flowers.repositories.FlowerDTO;
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
import com.itacademy.sigma_team.trees.repositories.TreeDTO;
import com.itacademy.sigma_team.trees.use_cases.AddTreeUseCase;
import com.itacademy.sigma_team.trees.use_cases.DeleteTreeUseCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class App {
    public static void run() {

        FlowerGateway flowerGateway = new FlowerGateway() {

            @Override
            public void addFlower(FlowerDTO flowerDTO) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }

            @Override
            public FlowerDTO getFlower(String flowerId) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }

            @Override
            public void deleteFlower(FlowerDTO flowerDTO) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }

        };

        DecorationGateway decorationGateway = new DecorationGateway() {
            @Override
            public DecorationDTO getDecoration(Long decorationId) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }

            @Override
            public void deleteDecoration(DecorationDTO decorationDTO) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }

            @Override
            public void addDecoration(DecorationDTO decorationDTO) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }
        };

        TreeGateway treeGateway = new TreeGateway() {
            @Override
            public void addTree(TreeDTO treeDTO) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }

            @Override
            public TreeDTO getTree(Long treeId) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }

            @Override
            public void deleteTree(TreeDTO treeDTO) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }
        };

        TicketGateway ticketGateway = new TicketGateway() {
            @Override
            public TicketDTO add(TicketDTO dto) throws IOException {
                throw new UnsupportedOperationException("Not yet implemented.");
            }

            @Override
            public void delete(TicketDTO dto) throws IOException {
                throw new UnsupportedOperationException("Not yet implemented.");
            }
        };

        // Sample data
        List<Flower> flowers = Arrays.asList(
                new Flower("Red", 10.0),
                new Flower("Blue", 12.5, price -> price * 0.9) // Discounted flower
        );

        List<Tree> trees = Arrays.asList(
                new Tree("Oak", 3.2, 15.5),
                new Tree("Elm", 4.2, 12.5)
        );

        List<Decoration> decorations = Arrays.asList(
                new Decoration(Material.WOOD),
                new Decoration(Material.PLASTIC)
        );

        // Building the FlowerShop
        FlowerShop flowerShop = new FlowerShop.FlowerShopBuilder("Sigma Flower Shop")
                .flowers(flowers)
                .trees(trees)
                .decorations(decorations)
                .build();


        CliController cliController = new CliController(
                new AddFlowerUseCase(flower -> flowerGateway.addFlower(FlowerMapper.toDto(flower))),
                new DeleteFlowerUseCase(flower -> flowerGateway.deleteFlower(FlowerMapper.toDto(flower))),
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

    }
}
