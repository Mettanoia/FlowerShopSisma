package com.itacademy.sigma_team.domain;


import java.util.UUID;

public class Tree implements Buyable {
    private final String id;
    private final String name;
    private final double height;
    private final double price;
    private final int stock;
    public Tree(String name, double height, double price, int stock) {
        this(UUID.randomUUID().toString(), name, height, price, stock);
    }

    public Tree(String id, String name, double height, double price, int stock) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public double calculatePrice() {
        return 0;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getSpecies() {
        return name;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
