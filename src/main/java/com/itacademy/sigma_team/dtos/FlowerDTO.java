package com.itacademy.sigma_team.dtos;

public record FlowerDTO(String id, String name, String color, double price, int stock) implements TicketItem {

}
