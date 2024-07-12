package com.itacademy.sigma_team.services;

import com.itacademy.sigma_team.domain.*;
import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.dtos.TreeDTO;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;
import com.itacademy.sigma_team.gateways.DecorationGateway;
import com.itacademy.sigma_team.gateways.TreeGateway;
import com.itacademy.sigma_team.tickets.repositories.TicketItemDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class TicketService {

    private final TicketRepository ticketRepository;
    private final FlowerGateway flowerGateway;
    private final TreeGateway treeGateway;
    private final DecorationGateway decorationGateway;

    public TicketService(TicketRepository ticketRepository, FlowerGateway flowerGateway, TreeGateway treeGateway, DecorationGateway decorationGateway) {
        this.ticketRepository = ticketRepository;
        this.flowerGateway = flowerGateway;
        this.treeGateway = treeGateway;
        this.decorationGateway = decorationGateway;
    }

    public String generateTicketId() {
        return UUID.randomUUID().toString();
    }

    public void addItemToTicket(String ticketId, TicketItemDTO itemDTO) {
        TicketItem item = new TicketItem(ticketId, itemDTO.productId(), itemDTO.productType(), itemDTO.quantity(), itemDTO.price());
        ticketRepository.saveItem(item);
    }

    public Ticket getTicket(String ticketId) {
        List<TicketItem> items = ticketRepository.findItemsByTicketId(ticketId);
        double total = calculateTotal(items);
        return new Ticket(ticketId, LocalDateTime.now(), items, total);
    }

    private double calculateTotal(List<TicketItem> items) {
        double total = 0.0;
        for (TicketItem item : items) {
            switch (item.getProductType()) {
                case "flower":
                    FlowerDTO flower = flowerGateway.get(item.getProductId());
                    total += flower.price() * item.getQuantity();
                    break;
                case "tree":
                    TreeDTO tree = treeGateway.getTree(item.getProductId());
                    total += tree.price() * item.getQuantity();
                    break;
                case "decoration":
                    DecorationDTO decoration = decorationGateway.getDecoration(item.getProductId());
                    total += decoration.price() * item.getQuantity();
                    break;
            }
        }
        return total;
    }
}




