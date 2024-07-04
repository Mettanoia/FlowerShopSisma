package com.itacademy.sigma_team.flowers.use_cases;

public interface FlowerGateway {
    void addFlower(FlowerDTO flowerDTO);
    FlowerDTO getFlower(Long flowerId);
    void deleteFlower(FlowerDTO flowerDTO);
}
