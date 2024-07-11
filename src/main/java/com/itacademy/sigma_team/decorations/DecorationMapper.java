package com.itacademy.sigma_team.decorations;

import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.domain.Material;
import com.itacademy.sigma_team.dtos.DecorationDTO;

// TODO This needs a better mapper for the id
public final class DecorationMapper {

    public static Decoration toDomain(DecorationDTO decorationDTO) {
        return new Decoration(
                decorationDTO.id(),
                decorationDTO.name(),
                Material.valueOf(decorationDTO.material().name()),
                decorationDTO.price(),
                decorationDTO.stock()
        );
    }

    public static DecorationDTO toDto(Decoration decoration) {
        return new DecorationDTO(
                decoration.getId(),
                decoration.getName(),
                Material.valueOf(decoration.getMaterial().name()),
                decoration.getPrice(),
                decoration.getStock()
        );
    }
}
