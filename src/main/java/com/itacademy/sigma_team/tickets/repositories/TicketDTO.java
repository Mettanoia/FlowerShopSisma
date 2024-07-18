package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.dtos.TicketItem;
import com.itacademy.sigma_team.dtos.TreeDTO;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

public record TicketDTO(String id, LocalDateTime dateTime, Collection<TicketItem> items) {

    @Override
    public Collection<TicketItem> items() {
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

}
