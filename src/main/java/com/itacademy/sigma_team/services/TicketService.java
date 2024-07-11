package com.itacademy.sigma_team.services;

import com.itacademy.sigma_team.domain.*;
import com.itacademy.sigma_team.tickets.repositories.TicketItemDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public String generateTicketId() {
        return UUID.randomUUID().toString();
    }

    public void addItemToTicket(String ticketId, TicketItemDTO itemDTO) {
        TicketItem item = new TicketItem(ticketId, itemDTO.productId(), itemDTO.productType(), itemDTO.quantity(), itemDTO.price());
        ticketRepository.saveItem(item);
    }

    public Ticket getTicket(String ticketId, List<Flower> flowers, List<Tree> trees, List<Decoration> decorations) {
        List<TicketItem> items = ticketRepository.findItemsByTicketId(ticketId);
        double total = calculateTotal(items, flowers, trees, decorations);
        return new Ticket(ticketId, LocalDateTime.now(), items, total);
    }

    private double calculateTotal(List<TicketItem> items, List<Flower> flowers, List<Tree> trees, List<Decoration> decorations) {
        double total = 0.0;
        for (TicketItem item : items) {
            switch (item.getProductType()) {
                case "flower":
                    total += flowers.stream()
                            .filter(f -> f.getId().equals(item.getProductId()))
                            .mapToDouble(f -> f.getPrice() * item.getQuantity())
                            .sum();
                    break;
                case "tree":
                    total += trees.stream()
                            .filter(t -> t.getId().equals(item.getProductId()))
                            .mapToDouble(t -> t.getPrice() * item.getQuantity())
                            .sum();
                    break;
                case "decoration":
                    total += decorations.stream()
                            .filter(d -> d.getId().equals(item.getProductId()))
                            .mapToDouble(d -> d.getPrice() * item.getQuantity())
                            .sum();
                    break;
            }
        }
        return total;
    }
}



