package com.itacademy.sigma_team.domain;


import java.util.function.Function;

public final class Tree implements Buyable {

    private final String species;
    private final double price;
    private final Function<Double, Double> calculatePriceStrategy;

    public Tree(String species, double price) {
        this.species = species;
        this.price = price;
        this.calculatePriceStrategy = aDouble -> aDouble; // Función identidad
    }

    public Tree(String species, double price, Function<Double, Double> calculatePriceStrategy) {
        this.species = species;
        this.price = price;
        this.calculatePriceStrategy = calculatePriceStrategy;
    }

    public String getSpecies() {
        return species;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public double calculatePrice() {
        return this.calculatePriceStrategy.apply(this.price);
    }
}
