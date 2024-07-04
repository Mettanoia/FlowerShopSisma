package com.itacademy.sigma_team;

import com.itacademy.sigma_team.cli_controller.CliController;
import com.itacademy.sigma_team.flowers.FlowerMapper;
import com.itacademy.sigma_team.flowers.use_cases.AddFlowerUseCase;
import com.itacademy.sigma_team.flowers.use_cases.FlowerDTO;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;

public class Main {
    public static void main(String[] args) {

        FlowerGateway flowerGateway = new FlowerGateway() {
            @Override
            public void addFlower(FlowerDTO flowerDTO) {

            }

            @Override
            public FlowerDTO getFlower(Long flowerId) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void deleteFlower(FlowerDTO flowerDTO) {
                throw new UnsupportedOperationException();
            }
        };

        CliController cliController = new CliController(
                new AddFlowerUseCase(
                        flower -> flowerGateway.addFlower(FlowerMapper.toDto(flower))
                )
        );

    }
}