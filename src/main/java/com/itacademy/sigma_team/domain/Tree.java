package com.itacademy.sigma_team.domain;



public class Tree implements Buyable {
    private final int id;
    private final String species;
    private final double height;
    private final double price;

    public Tree(int id, String species, double height, double price) {
        this.id = id;
        this.species = species;
        this.height = height;
        this.price = price;
    }

    @Override
    public double calculatePrice() {
        return 0;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getSpecies() {
        return species;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
