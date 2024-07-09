package com.itacademy.sigma_team.domain;

sealed public interface TicketItem permits Flower, Tree {

    void accept(TicketItemVisitor ticketItemVisitor);

}
