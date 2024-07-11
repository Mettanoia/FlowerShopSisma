package com.itacademy.sigma_team.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Ticket {

    private final String id;
    private final LocalDateTime dateTime;
    private final List<TicketItem> items;
    private double total;

    public Ticket(String id, LocalDateTime dateTime, List<TicketItem> items, double total) {
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

    public void addItem(TicketItem item) {
        items.add(item);
        total += item.calculateTotalPrice();
    }

    public void removeItem(TicketItem item) {
        items.remove(item);
        total -= item.calculateTotalPrice();
    }



    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("Ticket Date: ").append(dateTime.format(formatter)).append("\n");
        sb.append("Items:\n");
        for (TicketItem item : items) {
            sb.append(String.format(" - %s: %s, Quantity: %d, Price: %.2f\n",
                    item.getProductType(),
                    item.getProductId(),
                    item.getQuantity(),
                    item.getPrice()));
        }
        sb.append(String.format("Total: %.2f\n", total));
        sb.append("Thank you for your visit!");
        return sb.toString();
    }
}

