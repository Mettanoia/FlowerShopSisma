package com.itacademy.sigma_team.domain;


import java.util.function.Function;

public final class Flower implements Buyable {

    private final String color;
    private final double price;
    private final Function<Double, Double> calculatePriceStrategy;

    public Flower(String color, double price) {
        this.color = color;
        this.price = price;
        this.calculatePriceStrategy = aDouble -> aDouble; // Identity function
    }

    public Flower(String color, double price, Function<Double, Double> calculatePriceStrategy) {
        this.color = color;
        this.price = price;
        this.calculatePriceStrategy = calculatePriceStrategy;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public double calculatePrice() {
        return this.calculatePriceStrategy.apply(this.price);
    }

}
