package com.itacademy.sigma_team.tickets.use_cases;

import com.itacademy.sigma_team.domain.Ticket;

import java.util.function.Function;

public final class GetTicketUseCase {

    private final Function<String, Ticket> getTicketMixin;

    public GetTicketUseCase(Function<String, Ticket> getTicketMixin) {
        this.getTicketMixin = getTicketMixin;
    }

    public Ticket exec(String ticketId) {
        return this.getTicketMixin.apply(ticketId);
    }

}
