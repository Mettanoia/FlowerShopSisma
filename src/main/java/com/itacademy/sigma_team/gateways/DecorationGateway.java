package com.itacademy.sigma_team.gateways;

import com.itacademy.sigma_team.dtos.DecorationDTO;

public interface DecorationGateway {
    DecorationDTO getDecoration(Long decorationId);
    void deleteDecoration(DecorationDTO decorationDTO);
    void addDecoration(DecorationDTO decorationDTO);
}
