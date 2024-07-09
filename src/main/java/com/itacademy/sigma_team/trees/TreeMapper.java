package com.itacademy.sigma_team.trees;


import com.itacademy.sigma_team.domain.Tree;
import com.itacademy.sigma_team.dtos.TreeDTO;


public final class TreeMapper {

    public static Tree toDomain(TreeDTO treeDTO) {
        return new Tree(treeDTO.id(), treeDTO.species(), treeDTO.height(), treeDTO.price());
    }

    public static TreeDTO toDto(Tree tree) {
        return new TreeDTO(tree.getId(), tree.getSpecies(), tree.getHeight(), tree.getPrice());
    }
}
