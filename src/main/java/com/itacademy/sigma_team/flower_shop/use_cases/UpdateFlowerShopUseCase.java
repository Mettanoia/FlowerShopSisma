package com.itacademy.sigma_team.flower_shop.use_cases;

import com.itacademy.sigma_team.decorations.repositories.DecorationMapper;
import com.itacademy.sigma_team.decorations.use_cases.DecorationGateway;
import com.itacademy.sigma_team.flower_shop.FlowerShop;
import com.itacademy.sigma_team.flowers.repositories.FlowerMapper;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;
import com.itacademy.sigma_team.trees.repositories.TreeMapper;
import com.itacademy.sigma_team.trees.use_cases.TreeGateway;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class UpdateFlowerShopUseCase {

    private final FlowerGateway flowerGateway;
    private final TreeGateway treeGateway;
    private final DecorationGateway decorationGateway;


    public UpdateFlowerShopUseCase(FlowerGateway flowerGateway, TreeGateway treeGateway, DecorationGateway decorationGateway) {
        this.flowerGateway = flowerGateway;
        this.treeGateway = treeGateway;
        this.decorationGateway = decorationGateway;
    }

    public FlowerShop exec(FlowerShop flowerShop) {
        try {
            return new FlowerShop.FlowerShopBuilder(flowerShop.name)

                    .flowers(

                            flowerGateway.getAll().stream()
                                    .map(FlowerMapper::toDomain)
                                    .collect(Collectors.toSet())

                    )

                    .trees(


                            treeGateway.getAll().stream()
                                    .map(TreeMapper::toDomain)
                                    .collect(Collectors.toSet())

                    )

                    .decorations(

                            decorationGateway.getAll().stream()
                                    .map(DecorationMapper::toDomain)
                                    .collect(Collectors.toSet())

                    )

                    .build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



}
