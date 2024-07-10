package com.itacademy.sigma_team.domain;

import java.util.UUID;
import java.util.function.Function;

public final class Tree implements Buyable {

    private final String id;
    private final String name;
    private final double height;
    private final double price;
    private int stock;
    private final Function<Double, Double> calculatePriceStrategy;

    public Tree(String id, String name, double height, double price, int stock) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.price = price;
        this.stock = stock;
        this.calculatePriceStrategy = aDouble -> aDouble; // Función identidad
    }

    public Tree(String id, String name, double height, double price, Function<Double, Double> calculatePriceStrategy) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.price = price;
        this.calculatePriceStrategy = calculatePriceStrategy;
    }

    public String getId() {
        return id;
    }
    public double getHeight() {
        return height;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public double calculatePrice() {
        return this.calculatePriceStrategy.apply(this.price);
    }

    @Override
    public String toString() {
        return "Tree{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}

