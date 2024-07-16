package com.itacademy.sigma_team.domain;

import com.itacademy.sigma_team.dtos.TicketItem;

import java.util.UUID;
import java.util.function.Function;

public final class Decoration implements Buyable, Product {

    private final String id;
    private final String name;
    private final Material material;
    private final double price;
    private int stock;
    private final Function<Double, Double> calculatePriceStrategy;

    public Decoration(String name, Material material, double price, int stock) {
        this(UUID.randomUUID().toString(), name, material, price, stock);
    }

    public Decoration(String id, String name, Material material, double price, int stock) {
        this.id = id;
        this.name = name;
        this.material = material;
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

    public Material getMaterial() {
        return material;
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
        return "Decoration{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", material=" + material +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}

