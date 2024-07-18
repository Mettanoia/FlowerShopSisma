package com.itacademy.sigma_team.domain;

import java.lang.reflect.InvocationTargetException;

public sealed interface Product permits Flower, Tree, Decoration{

    static int getStock(Product product) {
        try {
            return (int) product.getClass().getMethod("getStock").invoke(product);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    static String getName(Product selectedItem) {
        try {
            return (String) selectedItem.getClass().getMethod("getName").invoke(selectedItem);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    static double getPrice(Product selectedItem) {
        try {
            return (double) selectedItem.getClass().getMethod("getPrice").invoke(selectedItem);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    static String getId(Product selectedItem) {
        try {
            return (String) selectedItem.getClass().getMethod("getId").invoke(selectedItem);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

}
