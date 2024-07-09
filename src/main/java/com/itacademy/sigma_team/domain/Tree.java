package com.itacademy.sigma_team.domain;


import java.util.function.Function;

public final class Tree implements Buyable {

    private final String id;
    private final String species;
    private final double height;
    private final double price;
    private final Function<Double, Double> calculatePriceStrategy;

    public Tree(String id, String species, double height, double price) {
        this.id = id;
        this.species = species;
        this.height = height;
        this.price = price;
        this.calculatePriceStrategy = aDouble -> aDouble; // Función identidad
    }

    public Tree(String id, String species, double height, double price, Function<Double, Double> calculatePriceStrategy) {
        this.id = id;
        this.species = species;
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
