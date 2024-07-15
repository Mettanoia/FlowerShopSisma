package com.itacademy.sigma_team.tickets.use_cases;

import com.itacademy.sigma_team.domain.Ticket;

import java.util.Collection;
import java.util.function.Supplier;

public final class GetAllTicketsUseCase {

    private final Supplier<Collection<Ticket>> getAllTicketsMixin;

    public GetAllTicketsUseCase(Supplier<Collection<Ticket>> getAllTicketsMixin) {
        this.getAllTicketsMixin = getAllTicketsMixin;
    }

    public Collection<Ticket> exec() {
        return this.getAllTicketsMixin.get();
    }

}
