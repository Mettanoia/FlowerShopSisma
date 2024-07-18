package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.decorations.repositories.DecorationMapper;
import com.itacademy.sigma_team.domain.*;
import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.dtos.TicketItem;
import com.itacademy.sigma_team.dtos.TreeDTO;
import com.itacademy.sigma_team.flowers.repositories.FlowerMapper;
import com.itacademy.sigma_team.trees.repositories.TreeMapper;

import java.util.stream.Collectors;

public final class TicketMappers {

    public static TicketDTO toDto(Ticket ticket) {
        return new TicketDTO(ticket.getId(), ticket.getDateTime(), ticket.getItems().stream().map(TicketMappers::toDto).toList());
    }

    public static Ticket toDomain(TicketDTO ticketDTO) {
        return new Ticket(ticketDTO.id(), ticketDTO.dateTime(), ticketDTO.items().stream().map(TicketMappers::toDomain).toList());
    }

    public static Product toDomain(TicketItem ticketItem) {
        return switch (ticketItem) {
            case DecorationDTO decorationDTO -> DecorationMapper.toDomain(decorationDTO);
            case FlowerDTO flowerDTO -> FlowerMapper.toDomain(flowerDTO);
            case TreeDTO treeDTO -> TreeMapper.toDomain(treeDTO);
        };
    }

    public static TicketItem toDto(Product product) {
        return switch (product) {
            case Decoration decoration -> DecorationMapper.toDto(decoration);
            case Flower flower -> FlowerMapper.toDto(flower);
            case Tree tree -> TreeMapper.toDto(tree);
        };
    }

}
