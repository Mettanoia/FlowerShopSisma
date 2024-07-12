package com.itacademy.sigma_team.tickets.use_cases;

import com.itacademy.sigma_team.domain.Ticket;
import com.itacademy.sigma_team.domain.TicketItem;

import java.util.List;
import java.util.function.Consumer;

public final class AddTicketUseCase {

    private final Consumer<Ticket> addTicketMixin;

    public AddTicketUseCase(Consumer<Ticket> addTicketMixin) {
        this.addTicketMixin = addTicketMixin;
    }

    public void exec(List<TicketItem> ticket) {
        this.addTicketMixin.accept(ticket);
    }

}
