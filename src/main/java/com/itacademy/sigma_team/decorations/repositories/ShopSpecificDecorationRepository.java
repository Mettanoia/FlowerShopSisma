package com.itacademy.sigma_team.decorations.repositories;

import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.flower_shop.repositories.ShopRepository;
import com.itacademy.sigma_team.decorations.use_cases.DecorationGateway;

import java.util.Collection;
import java.util.function.Supplier;

public final class ShopSpecificDecorationRepository implements DecorationGateway {

    private static ShopSpecificDecorationRepository instance;
    private static Supplier<String> shopNameSupplier;
    private final DecorationSqlRepository decorationSqlRepository;
    private final ShopRepository shopRepository;
    private static String shopName;

    public ShopSpecificDecorationRepository() {
        this.decorationSqlRepository = new DecorationSqlRepository();
        this.shopRepository = new ShopRepository();
    }

    public static ShopSpecificDecorationRepository getInstance() {
        if (instance == null) {
            instance = new ShopSpecificDecorationRepository();
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
    public void add(DecorationDTO decorationDTO) {
        decorationSqlRepository.add(decorationDTO);
        addToShop(decorationDTO.id());
    }

    public void addToShop(String decorationId) {
        String shopId = shopRepository.getShopIdByName(getShopName());
        if (shopId != null) {
            decorationSqlRepository.addToShop(shopId, decorationId);
        } else {
            throw new IllegalArgumentException("Invalid shop name: " + getShopName());
        }
    }

    @Override
    public DecorationDTO get(String decorationId) {
        return decorationSqlRepository.get(decorationId, getShopName());
    }

    @Override
    public Collection<DecorationDTO> getAll() {
        return decorationSqlRepository.getAll(getShopName());
    }

    @Override
    public void delete(DecorationDTO decorationDTO) {
        decorationSqlRepository.delete(decorationDTO.id(), getShopName());
    }

    public void decrementStock(String decorationId, int quantityPurchased) {
        decorationSqlRepository.decrementStock(decorationId, quantityPurchased, getShopName());
    }
}
