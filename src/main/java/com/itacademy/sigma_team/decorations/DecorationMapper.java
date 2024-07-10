
package com.itacademy.sigma_team.decorations;

import com.itacademy.sigma_team.domain.Decoration;
import com.itacademy.sigma_team.dtos.DecorationDTO;

public final class DecorationMapper {

    public static Decoration toDomain(DecorationDTO decorationDTO) {
        return new Decoration(
                decorationDTO.id(),
                decorationDTO.name(),
                decorationDTO.material(),
                decorationDTO.price(),
                decorationDTO.stock()
        );
    }

    public static DecorationDTO toDto(Decoration decoration) {
        return new DecorationDTO(
                decoration.getId(),
                decoration.getName(),
                decoration.getMaterial(),
                decoration.getPrice(),
                decoration.getStock()
        );
    }
}

