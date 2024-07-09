package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.dtos.TicketItem;

import java.util.Collection;

public record TicketDTO(String id, Collection<TicketItem> ticketItems) { }
