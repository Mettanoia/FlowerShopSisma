package com.itacademy.sigma_team.trees;


import com.itacademy.sigma_team.domain.Tree;
import com.itacademy.sigma_team.trees.repositories.TreeDTO;


public final class TreeMapper {

    public static Tree toDomain(TreeDTO treeDTO) {
        return new Tree(treeDTO.species(), treeDTO.price());
    }

    public static TreeDTO toDto(Tree tree) {
        return new TreeDTO(tree.getSpecies(), tree.getPrice());
    }
}
