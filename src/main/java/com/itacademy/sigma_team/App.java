package com.itacademy.sigma_team;

import com.itacademy.sigma_team.cli_controller.CliController;
import com.itacademy.sigma_team.decorations.DecorationMapper;
import com.itacademy.sigma_team.decorations.repositories.DecorationDTO;
import com.itacademy.sigma_team.decorations.use_cases.AddDecorationUseCase;
import com.itacademy.sigma_team.decorations.use_cases.DeleteDecorationUseCase;
import com.itacademy.sigma_team.flowers.FlowerMapper;
import com.itacademy.sigma_team.flowers.repositories.FlowerDTO;
import com.itacademy.sigma_team.flowers.use_cases.AddFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.DeleteFlowerUseCase;
import com.itacademy.sigma_team.gateways.DecorationGateway;
import com.itacademy.sigma_team.gateways.FlowerGateway;

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

        CliController cliController = new CliController(
                new AddFlowerUseCase(flower -> flowerGateway.addFlower(FlowerMapper.toDto(flower))),
                new DeleteFlowerUseCase(flower -> flowerGateway.deleteFlower(FlowerMapper.toDto(flower))),
                new AddDecorationUseCase(decoration -> decorationGateway.addDecoration(DecorationMapper.toDto(decoration))),
                new DeleteDecorationUseCase(decoration -> decorationGateway.deleteDecoration(DecorationMapper.toDto(decoration)))
        );

    }
}
