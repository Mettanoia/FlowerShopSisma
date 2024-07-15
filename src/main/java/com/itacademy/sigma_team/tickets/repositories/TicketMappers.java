package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.domain.Ticket;

public final class TicketMappers {
    public static TicketDTO toDto(Ticket ticket) {
        return new TicketDTO(ticket.getId(), ticket.getDateTime(), ticket.getItems());
    }

    public static Ticket toDomain(TicketDTO ticketDTO) {
        return new Ticket(ticketDTO.getId(), ticketDTO.getDateTime(), ticketDTO.getItems(), ticketDTO.getTotal());
    }
}
