package com.itacademy.sigma_team.gateways;

import com.itacademy.sigma_team.flowers.repositories.FlowerDTO;

public interface FlowerGateway {
    void addFlower(FlowerDTO flowerDTO);
    FlowerDTO getFlower(Long flowerId);
    void deleteFlower(FlowerDTO flowerDTO);
}
