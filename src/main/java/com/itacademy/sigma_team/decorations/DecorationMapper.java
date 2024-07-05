package com.itacademy.sigma_team.decorations;

import com.itacademy.sigma_team.decorations.repositories.DecorationDTO;
import com.itacademy.sigma_team.domain.Decoration;

// TODO This needs a better mapper for the id
public final class DecorationMapper {

    public static DecorationDTO toDto(Decoration decoration) {
        return new DecorationDTO(0L, decoration.material());
    }

    public static Decoration toDomain(DecorationDTO decorationDTO) {
        return new Decoration(decorationDTO.material());
    }

}
