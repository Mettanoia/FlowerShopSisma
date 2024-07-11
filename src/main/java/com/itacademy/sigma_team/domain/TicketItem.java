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

    void accept(TicketItemVisitor ticketItemVisitor);

}
