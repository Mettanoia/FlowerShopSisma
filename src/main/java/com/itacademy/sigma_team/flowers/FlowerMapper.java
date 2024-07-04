package com.itacademy.sigma_team.flowers;

import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.flowers.use_cases.FlowerDTO;

public final class FlowerMapper {

    static Flower toDomain(FlowerDTO flowerDTO) {
        return new Flower(flowerDTO.color(), flowerDTO.price());
    }

    static FlowerDTO toDto(Flower flower) {
        return new FlowerDTO(flower.getColor(), flower.getPrice());
    }

}
