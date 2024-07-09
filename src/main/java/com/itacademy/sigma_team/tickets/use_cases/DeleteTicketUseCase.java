package com.itacademy.sigma_team.tickets.use_cases;

import com.itacademy.sigma_team.domain.Ticket;

import java.util.function.Consumer;

public final class DeleteTicketUseCase {

    private final Consumer<Ticket> deleteTickerMixin;

    public DeleteTicketUseCase(Consumer<Ticket> deleteTickerMixin) {
        this.deleteTickerMixin = deleteTickerMixin;
    }

    public void exec(Ticket ticker) {
        this.deleteTickerMixin.accept(ticker);
    }

}
