package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.domain.Ticket;

public final class TicketMappers {

    public static TicketDTO toDto(Ticket entity) {
        return new TicketDTO();
    }

    public static Ticket toDomain(TicketDTO dto) {
        return new Ticket();
    }

}
