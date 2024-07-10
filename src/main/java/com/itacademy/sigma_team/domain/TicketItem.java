package com.itacademy.sigma_team.domain;

public class TicketItem {
    private String ticketId;
    private String productId;
    private String productType;
    private int quantity;
    private double price;

    public TicketItem(String ticketId, String productId, String productType, int quantity, double price) {
        this.ticketId = ticketId;
        this.productId = productId;
        this.productType = productType;
        this.quantity = quantity;
        this.price = price;
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

    public double calculateTotalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return "TicketItem{" +
                "ticketId='" + ticketId + '\'' +
                ", productId='" + productId + '\'' +
                ", productType='" + productType + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}



