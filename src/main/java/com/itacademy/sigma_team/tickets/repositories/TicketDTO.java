package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.dtos.TicketItem;
import com.itacademy.sigma_team.dtos.TreeDTO;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class TicketDTO {
    private final String id;
    private final LocalDateTime dateTime;
    private final Collection<TicketItem> items;

    public TicketDTO(String id, LocalDateTime dateTime, List<TicketItem> items) {
        this.id = id;
        this.dateTime = dateTime;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Collection<TicketItem> getItems() {
        return Collections.unmodifiableCollection(items);
    }

    public double getTotal() {
        return items.stream().mapToDouble(
                e -> e instanceof FlowerDTO ?
                        ((FlowerDTO) e).price() :
                        e instanceof TreeDTO ?
                                ((TreeDTO) e).price() :
                                e instanceof DecorationDTO ?
                                        ((DecorationDTO) e).price() :
                                        0.0
        ).sum();
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
                "id='" + id + '\'' +
                ", dateTime=" + dateTime +
                ", items=" + items +
                ", total=" + getTotal() +
                '}';
    }

}
