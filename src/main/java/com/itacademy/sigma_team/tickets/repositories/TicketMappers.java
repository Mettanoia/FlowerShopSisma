package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.domain.Ticket;
import com.itacademy.sigma_team.domain.TicketItem;

import java.util.List;
import java.util.stream.Collectors;

public final class TicketMappers {

    public static TicketDTO toDto(Ticket entity) {
        List<TicketItemDTO> items = entity.getItems().stream()
                .map(item -> new TicketItemDTO(item.getProductId(), item.getProductType(), item.getQuantity(), item.getPrice()))
                .collect(Collectors.toList());
        return new TicketDTO(entity.getId(), entity.getDateTime(), items, entity.getTotal());
    }

    public static Ticket toDomain(TicketDTO dto) {
        List<TicketItem> items = dto.getItems().stream()
                .map(item -> new TicketItem(dto.getId(), item.productId(), item.productType(), item.quantity(), item.price()))
                .collect(Collectors.toList());
        return new Ticket(dto.getId(), dto.getDateTime(), items, dto.getTotal());
    }
}


