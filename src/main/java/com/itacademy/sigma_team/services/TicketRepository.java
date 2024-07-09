package com.itacademy.sigma_team.services;

import com.itacademy.sigma_team.domain.TicketItem;

import java.util.List;

public interface TicketRepository {
    void saveItem(TicketItem item);
    List<TicketItem> findItemsByTicketId(String ticketId);
}

