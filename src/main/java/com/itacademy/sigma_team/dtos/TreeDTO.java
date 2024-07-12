package com.itacademy.sigma_team.dtos;


public record TreeDTO(String id, String name, double height, double price, int stock) implements TicketItem {
}

