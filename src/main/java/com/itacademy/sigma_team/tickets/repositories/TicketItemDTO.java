package com.itacademy.sigma_team.tickets.repositories;

import java.time.LocalDateTime;

public record TicketItemDTO(String productId, String productType, int quantity, double price, LocalDateTime timestamp) {
}




