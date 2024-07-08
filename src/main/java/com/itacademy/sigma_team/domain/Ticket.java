package com.itacademy.sigma_team.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Ticket {

    private final String id;
    private final LocalDateTime dateTime;
    private final List<TicketItem> items;

    public Ticket() {
        this.id = UUID.randomUUID().toString();
        this.dateTime = LocalDateTime.now();
        this.items = new ArrayList<>();
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

