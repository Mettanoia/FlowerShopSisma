package com.itacademy.sigma_team.flowers.repositories;

import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.flower_shop.repositories.ShopRepository;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;

import java.util.Collection;
import java.util.function.Supplier;

public final class ShopSpecificFlowerRepository implements FlowerGateway {

    private static ShopSpecificFlowerRepository instance;
    private static Supplier<String> shopNameSupplier;
    private final FlowerSqlRepository flowerSqlRepository;
    private final ShopRepository shopRepository;
    private static String shopName;

    public ShopSpecificFlowerRepository() {
        this.flowerSqlRepository = new FlowerSqlRepository();
        this.shopRepository = new ShopRepository();
    }

    public static ShopSpecificFlowerRepository getInstance() {
        if (instance == null) {
            instance = new ShopSpecificFlowerRepository();
        }
        return instance;
    }

    public static void setShopNameSupplier(Supplier<String> supplier) {
        if (shopNameSupplier == null) {
            shopNameSupplier = supplier;
        }
    }

    private String getShopName() {
        if (shopName == null && shopNameSupplier != null) {
            shopName = shopNameSupplier.get();
        }
        if (shopName == null) {
            throw new IllegalStateException("Shop name has not been set.");
        }
        return shopName;
    }

    @Override
    public void add(FlowerDTO flowerDTO) {
        flowerSqlRepository.add(flowerDTO);
        addToShop(flowerDTO.id());
    }

    public void addToShop(String flowerId) {
        String shopId = shopRepository.getShopIdByName(getShopName());
        if (shopId != null) {
            flowerSqlRepository.addToShop(shopId, flowerId);
        } else {
            throw new IllegalArgumentException("Invalid shop name: " + getShopName());
        }
    }

    @Override
    public FlowerDTO get(String flowerId) {
        return flowerSqlRepository.get(flowerId, getShopName());
    }

    @Override
    public Collection<FlowerDTO> getAll() {
        return flowerSqlRepository.getAll(getShopName());
    }

    @Override
    public void delete(FlowerDTO flowerDTO) {
        flowerSqlRepository.delete(flowerDTO.id(), getShopName());
    }

}
