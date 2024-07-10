package com.itacademy.sigma_team.dtos;

import com.itacademy.sigma_team.domain.Material;

public record DecorationDTO(String id, String name, Material material, double price, int stock) implements TicketItem {
}
