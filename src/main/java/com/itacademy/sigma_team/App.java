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
import com.itacademy.sigma_team.gateways.DecorationGateway;
import com.itacademy.sigma_team.gateways.FlowerGateway;
import com.itacademy.sigma_team.print_stock.use_cases.PrintStockUseCase;

import java.util.Arrays;
import java.util.List;

public final class App {
    public static void run() {

        FlowerGateway flowerGateway = new FlowerGateway() {

            @Override
            public void addFlower(FlowerDTO flowerDTO) {throw new UnsupportedOperationException();}

            @Override
            public FlowerDTO getFlower(Long flowerId) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void deleteFlower(FlowerDTO flowerDTO) {
                throw new UnsupportedOperationException();
            }

        };

        DecorationGateway decorationGateway = new DecorationGateway() {
            @Override
            public DecorationDTO getDecoration(Long decorationId) { throw new UnsupportedOperationException(); }

            @Override
            public void deleteDecoration(DecorationDTO decorationDTO) { throw new UnsupportedOperationException(); }

            @Override
            public void addDecoration(DecorationDTO decorationDTO) { throw new UnsupportedOperationException(); }
        };

        // Sample data
        List<Flower> flowers = Arrays.asList(
                new Flower("Red", 10.0),
                new Flower("Blue", 12.5, price -> price * 0.9) // Discounted flower
        );

        List<Tree> trees = Arrays.asList(
                new Tree(5.5),
                new Tree(3.0)
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
                new PrintStockUseCase(flowerShop)
        );

        cliController.printStock();

    }
}
