package com.itacademy.sigma_team.common;

public class ServiceLocator {

    private static String shopName;

    public static void setShopName(String name) {
        shopName = name;
    }

    public static String getShopName() {
        if (shopName == null) {
            throw new IllegalStateException("Shop name has not been set.");
        }
        return shopName;
    }

}
