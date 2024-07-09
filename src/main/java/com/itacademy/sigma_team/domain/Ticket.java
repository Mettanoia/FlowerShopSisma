package com.itacademy.sigma_team.domain;

import com.itacademy.sigma_team.dtos.TicketItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public final class Ticket {

    private final String id;
    private final LocalDateTime dateTime;
    private final Collection<TicketItem> items;

    public Ticket() {
        this(UUID.randomUUID().toString(), LocalDateTime.now(), new ArrayList<>());
    }

    public Ticket(String id, LocalDateTime dateTime, Collection<TicketItem> items) {
        this.id = id;
        this.dateTime = dateTime;
        this.items = items;
    }

    public Ticket(String id, Collection<TicketItem> items) {
        this(id, LocalDateTime.now(), items);
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<TicketItem> getItems() {
        return new ArrayList<>(items);
    }

    public void addItem(TicketItem item) {
        items.add(item);
    }

    public void removeItem(TicketItem item) {
        items.remove(item);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", dateTime=" + dateTime +
                ", items=" + items +
                '}';
    }
}

