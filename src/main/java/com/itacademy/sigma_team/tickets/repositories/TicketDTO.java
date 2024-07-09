package com.itacademy.sigma_team.tickets.repositories;

import java.time.LocalDateTime;
import java.util.List;

public class TicketDTO {
    private String id;
    private LocalDateTime dateTime;
    private List<TicketItemDTO> items;
    private double total;

    public TicketDTO(String id, LocalDateTime dateTime, List<TicketItemDTO> items, double total) {
        this.id = id;
        this.dateTime = dateTime;
        this.items = items;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<TicketItemDTO> getItems() {
        return items;
    }

    public void setItems(List<TicketItemDTO> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
                "id='" + id + '\'' +
                ", dateTime=" + dateTime +
                ", items=" + items +
                ", total=" + total +
                '}';
    }
}
