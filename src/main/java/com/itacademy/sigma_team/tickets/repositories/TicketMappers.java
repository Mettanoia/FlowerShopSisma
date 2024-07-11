package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.domain.Ticket;

public final class TicketMappers {

    public static TicketDTO toDto(Ticket entity) {
        List<TicketItemDTO> items = entity.getItems().stream()
                .map(item -> new TicketItemDTO(item.getProductId(), item.getProductType(), item.getQuantity(), item.getPrice()))
                .collect(Collectors.toList());
        return new TicketDTO(entity.getId(), entity.getDateTime(), items, entity.getTotal());
    }

    public static Ticket toDomain(TicketDTO dto) {
        List<TicketItem> items = dto.items().stream()
                .map(item -> new TicketItem(dto.id(), item.getProductId(), item.getProductType(), item.getQuantity(), item.getPrice()))
                .collect(Collectors.toList());
        return new Ticket(dto.id(), dto.dateTime(), items, dto.total());
    }
}
