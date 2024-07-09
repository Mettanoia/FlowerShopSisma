package com.itacademy.sigma_team.gateways;

import com.itacademy.sigma_team.decorations.repositories.DecorationDTO;

public interface DecorationGateway {
    DecorationDTO getDecoration(String decorationId);



    void deleteDecoration(DecorationDTO decorationDTO);
    void addDecoration(DecorationDTO decorationDTO);
}
