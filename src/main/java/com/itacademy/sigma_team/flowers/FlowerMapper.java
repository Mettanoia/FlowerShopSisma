package com.itacademy.sigma_team.flowers;

import com.itacademy.sigma_team.domain.Flower;
import com.itacademy.sigma_team.dtos.FlowerDTO;

public final class FlowerMapper {

    public static Flower toDomain(FlowerDTO flowerDTO) {
        return new Flower(flowerDTO.id(), flowerDTO.name(), flowerDTO.color(), flowerDTO.price(), flowerDTO.stock());
    }

    public static FlowerDTO toDto(Flower flower) {
        return new FlowerDTO(flower.getId(), flower.getName(), flower.getColor(), flower.getPrice(), flower.getStock());
    }
}

