package com.itacademy.sigma_team.flowers;

import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.flowers.repositories.FlowerDTO;

public final class FlowerMapper {

    public static Flower toDomain(FlowerDTO flowerDTO) {
        return new Flower(flowerDTO.color(), flowerDTO.price());
    }

    public static FlowerDTO toDto(Flower flower) {
        return new FlowerDTO(flower.getColor(), flower.getPrice());
    }

}
