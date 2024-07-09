package com.itacademy.sigma_team.trees;

import com.itacademy.sigma_team.domain.Tree;
import com.itacademy.sigma_team.trees.repositories.TreeDTO;


public class TreeMapper {
    public static TreeDTO toDto(Tree tree) {
        return new TreeDTO(tree.getId(), tree.getSpecies(), tree.getHeight(), tree.getPrice());
    }
}

