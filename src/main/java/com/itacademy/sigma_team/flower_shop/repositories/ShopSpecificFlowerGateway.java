package com.itacademy.sigma_team.flower_shop.repositories;

import com.itacademy.sigma_team.common.ServiceLocator;
import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.flowers.repositories.FlowerSqlRepository;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;

import java.util.Collection;

public final class ShopSpecificFlowerGateway implements FlowerGateway {

    private final FlowerSqlRepository flowerSqlRepository;
    private final ShopRepository shopRepository;

    public ShopSpecificFlowerGateway() {
        this.flowerSqlRepository = new FlowerSqlRepository();
        this.shopRepository = new ShopRepository();
    }

    @Override
    public void add(FlowerDTO flowerDTO) {
        flowerSqlRepository.add(flowerDTO);
    }

    public void addToShop(String flowerId) {
        String shopId = shopRepository.getShopIdByName(ServiceLocator.getShopName());
        if (shopId != null) {
            flowerSqlRepository.addToShop(shopId, flowerId);
        } else {
            throw new IllegalArgumentException("Invalid shop name: " + ServiceLocator.getShopName());
        }
    }

    @Override
    public FlowerDTO get(String flowerId) {
        return flowerSqlRepository.get(flowerId, ServiceLocator.getShopName());
    }

    @Override
    public Collection<FlowerDTO> getAll() {
        return flowerSqlRepository.getAll(ServiceLocator.getShopName());
    }

    @Override
    public void delete(FlowerDTO flowerDTO) {
        flowerSqlRepository.delete(flowerDTO.id(), ServiceLocator.getShopName());
    }

}
