package com.itacademy.sigma_team.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public final class Ticket {

    private final String id;
    private final LocalDateTime dateTime;
    private final Collection<Product> items;

    public Ticket(String id, LocalDateTime dateTime, Collection<Product> items) {
        this.id = id;
        this.dateTime = dateTime;
        this.items = Set.copyOf(items);
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<Product> getItems() {
        return new ArrayList<>(items);
    }

}

