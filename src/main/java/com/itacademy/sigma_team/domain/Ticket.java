package com.itacademy.sigma_team.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.dtos.TicketItem;
import com.itacademy.sigma_team.dtos.TreeDTO;

public final class Ticket {

    private final String id;
    private final LocalDateTime dateTime;
    private final Collection<TicketItem> items;
    private double total;

    public Ticket(String id, LocalDateTime dateTime, Collection<TicketItem> items, double total) {
        this.id = id;
        this.dateTime = dateTime;
        this.items = new ArrayList<>(items);
        this.total = total;
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

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("Ticket Date: ").append(dateTime.format(formatter)).append("\n");
        sb.append("Items:\n");
        for (TicketItem item : items) {
            sb.append(String.format(" - %s: %s, Quantity: %d, Price: %.2f\n",
                            getItemTicketType(item),
                            getProductId(item),
                            getProductStock(item),
                            getProductPrice(item)
                    )
            );
        }
        sb.append(String.format("Total: %.2f\n", total));
        sb.append("Thank you for your visit!");
        return sb.toString();
    }

    private static String getProductId(TicketItem item) {
        return switch (item) {
            case DecorationDTO decorationDTO -> decorationDTO.id();
            case FlowerDTO flowerDTO -> flowerDTO.id();
            case TreeDTO treeDTO -> treeDTO.id();
        };
    }

    private static int getProductStock(TicketItem item) {
        return switch (item) {
            case DecorationDTO decorationDTO -> decorationDTO.stock();
            case FlowerDTO flowerDTO -> flowerDTO.stock();
            case TreeDTO treeDTO -> treeDTO.stock();
        };
    }

    private static double getProductPrice(TicketItem item) {
        return switch (item) {
            case DecorationDTO decorationDTO -> decorationDTO.price();
            case FlowerDTO flowerDTO -> flowerDTO.price();
            case TreeDTO treeDTO -> treeDTO.price();
        };
    }

    private static String getItemTicketType(TicketItem item) {

        return item instanceof FlowerDTO ?
                        "Flower" :
                        item instanceof TreeDTO ?
                                "Tree" :
                                item instanceof DecorationDTO ?
                                        "Decoration" :
                                        "None";

    }

}

