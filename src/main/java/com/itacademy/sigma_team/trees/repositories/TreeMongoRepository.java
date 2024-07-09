package com.itacademy.sigma_team.trees.repositories;


import com.itacademy.sigma_team.dtos.TreeDTO;
import com.itacademy.sigma_team.gateways.TreeGateway;

public final class TreeMongoRepository implements TreeGateway {
    @Override
    public void addTree(TreeDTO treeDTO) {
        // Implementación para añadir un árbol a MongoDB
    }

    @Override
    public TreeDTO getTree(Long treeId) {
        // Implementación para obtener un árbol de MongoDB
        return null;
    }

    @Override
    public void deleteTree(TreeDTO treeDTO) {
        // Implementación para eliminar un árbol de MongoDB
    }
}


