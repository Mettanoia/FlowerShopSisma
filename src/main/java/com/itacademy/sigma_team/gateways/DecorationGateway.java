package com.itacademy.sigma_team.gateways;

import com.itacademy.sigma_team.dtos.DecorationDTO;

import java.util.Collection;

public interface DecorationGateway {
    DecorationDTO getDecoration(String decorationId);
    void deleteDecoration(DecorationDTO decorationDTO);
    void addDecoration(DecorationDTO decorationDTO);
    Collection<DecorationDTO> getAll();
}
