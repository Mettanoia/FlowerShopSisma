package com.itacademy.sigma_team.domain;

import java.util.UUID;
import java.util.function.Function;

public final class Flower implements Buyable {

    private final String id;
    private final String name;
    private final String color;
    private final double price;
    private int stock;
    private final Function<Double, Double> calculatePriceStrategy;

    public Flower(String name, String color, double price, int stock) {
        this(UUID.randomUUID().toString(), name, color, price, stock);
    }

    public Flower(String id, String name, String color, double price, int stock) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.price = price;
        this.stock = stock;
        this.calculatePriceStrategy = aDouble -> aDouble; // Función identidad
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
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



}
