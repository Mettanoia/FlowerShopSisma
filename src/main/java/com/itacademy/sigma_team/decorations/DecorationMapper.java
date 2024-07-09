package com.itacademy.sigma_team.decorations;

import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.domain.Decoration;

// TODO This needs a better mapper for the id
public final class DecorationMapper {

    public static DecorationDTO toDto(Decoration decoration) {
        return new DecorationDTO(decoration.id(), decoration.name(), decoration.material());
    }

    public static Decoration toDomain(DecorationDTO decorationDTO) {
        return new Decoration(decorationDTO.id(), decorationDTO.name(), decorationDTO.material());
    }

}
