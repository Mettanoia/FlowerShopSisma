package com.itacademy.sigma_team.domain;

sealed public interface TicketItem permits Flower {

    void accept(TicketItemVisitor ticketItemVisitor);

}
