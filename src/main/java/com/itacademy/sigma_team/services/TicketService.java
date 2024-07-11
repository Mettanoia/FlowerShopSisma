package com.itacademy.sigma_team.services;

import com.itacademy.sigma_team.domain.*;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;
import com.itacademy.sigma_team.gateways.DecorationGateway;
import com.itacademy.sigma_team.gateways.TreeGateway;
import com.itacademy.sigma_team.tickets.repositories.TicketItemDTO;
import com.itacademy.sigma_team.tickets.repositories.TicketSqlRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        TicketItem item = new TicketItem(
                ticketId,
                itemDTO.productId(),
                itemDTO.productType(),
                itemDTO.quantity(),
                itemDTO.price(),
                LocalDateTime.now() // Añadir el timestamp aquí
        );
        ticketRepository.saveItem(item);
    }

    public Ticket getTicket(String ticketId) {
        List<TicketItem> items = ticketRepository.findItemsByTicketId(ticketId);
        List<Flower> flowers = items.stream()
                .filter(item -> item.getProductType().equals("flower"))
                .map(item -> flowerGateway.get(item.getProductId()))
                .map(flowerDTO -> new Flower(flowerDTO.id(), flowerDTO.name(), flowerDTO.color(), flowerDTO.price(), flowerDTO.stock()))
                .collect(Collectors.toList());

        List<Tree> trees = items.stream()
                .filter(item -> item.getProductType().equals("tree"))
                .map(item -> treeGateway.getTree(item.getProductId()))
                .map(treeDTO -> new Tree(treeDTO.id(), treeDTO.name(), treeDTO.height(), treeDTO.price(), treeDTO.stock()))
                .collect(Collectors.toList());

        List<Decoration> decorations = items.stream()
                .filter(item -> item.getProductType().equals("decoration"))
                .map(item -> decorationGateway.getDecoration(item.getProductId()))
                .map(decorationDTO -> new Decoration(decorationDTO.id(), decorationDTO.name(), Material.valueOf(decorationDTO.material()), decorationDTO.price(), decorationDTO.stock()))
                .collect(Collectors.toList());

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






