package com.itacademy.sigma_team.trees.repositories;

import com.itacademy.sigma_team.dtos.TreeDTO;
import com.itacademy.sigma_team.flower_shop.repositories.ShopRepository;
import com.itacademy.sigma_team.trees.use_cases.TreeGateway;

import java.util.Collection;
import java.util.function.Supplier;

public final class ShopSpecificTreeRepository implements TreeGateway {

    private static ShopSpecificTreeRepository instance;
    private static Supplier<String> shopNameSupplier;
    private final TreeSqlRepository treeSqlRepository;
    private final ShopRepository shopRepository;
    private static String shopName;

    public ShopSpecificTreeRepository() {
        this.treeSqlRepository = new TreeSqlRepository();
        this.shopRepository = new ShopRepository();
    }

    public static ShopSpecificTreeRepository getInstance() {
        if (instance == null) {
            instance = new ShopSpecificTreeRepository();
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
    public void add(TreeDTO treeDTO) {
        treeSqlRepository.add(treeDTO);
        addToShop(treeDTO.id());
    }

    public void addToShop(String treeId) {

        String shopId = shopRepository.getShopIdByName(getShopName());

        if (shopId != null) {
            treeSqlRepository.addToShop(shopId, treeId);
        } else {
            throw new IllegalArgumentException("Invalid shop name: " + getShopName());
        }

    }

    @Override
    public TreeDTO get(String treeId) {
        return treeSqlRepository.get(treeId, getShopName());
    }

    @Override
    public Collection<TreeDTO> getAll() {
        return treeSqlRepository.getAll(getShopName());
    }

    @Override
    public void delete(TreeDTO treeDTO) {
        treeSqlRepository.delete(treeDTO.id(), getShopName());
    }

}
