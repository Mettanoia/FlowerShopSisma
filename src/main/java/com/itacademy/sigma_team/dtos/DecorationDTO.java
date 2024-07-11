package com.itacademy.sigma_team.dtos;

public record DecorationDTO(String id, String name, com.itacademy.sigma_team.domain.Material material, double price, int stock) implements TicketItem {
}
