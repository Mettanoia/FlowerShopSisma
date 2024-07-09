package com.itacademy.sigma_team.flowers.use_cases;

import com.itacademy.sigma_team.flowers.repositories.FlowerDTO;

public interface FlowerGateway {
    void addFlower(FlowerDTO flowerDTO);
    FlowerDTO getFlower(String flowerId);
    void deleteFlower(FlowerDTO flowerDTO);
}
