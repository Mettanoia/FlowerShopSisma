package com.itacademy.sigma_team.tickets.use_cases;

import com.itacademy.sigma_team.domain.Ticket;

import java.util.function.Consumer;

public final class AddTicketUseCase {

    private final Consumer<Ticket> addTicketMixin;

    public AddTicketUseCase(Consumer<Ticket> addTicketMixin) {
        this.addTicketMixin = addTicketMixin;
    }

    public void exec(Ticket ticket) {
        this.addTicketMixin.accept(ticket);
    }

}
