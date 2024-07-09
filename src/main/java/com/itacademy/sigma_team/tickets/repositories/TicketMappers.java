package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.domain.Ticket;

public final class TicketMappers {

    public static TicketDTO toDto(Ticket entity) {
        return new TicketDTO(entity.getId(), entity.getItems());
    }

    public static Ticket toDomain(TicketDTO dto) {
        return new Ticket(dto.id(), dto.ticketItems());
    }

}
