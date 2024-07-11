package com.itacademy.sigma_team.domain;

import java.time.LocalDateTime;

public class TicketItem {
    private String ticketId;
    private String productId;
    private String productType;
    private int quantity;
    private double price;
    private LocalDateTime timestamp;

    public TicketItem(String ticketId, String productId, String productType, int quantity, double price, LocalDateTime timestamp) {
        this.ticketId = ticketId;
        this.productId = productId;
        this.productType = productType;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double calculateTotalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return String.format("%s, Quantity: %d, Price: %.2f",
                productType,
                quantity,
                price);
    }
}




