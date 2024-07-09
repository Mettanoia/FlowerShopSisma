package com.itacademy.sigma_team.gateways;


import com.itacademy.sigma_team.dtos.TreeDTO;

public interface TreeGateway {
    void addTree(TreeDTO treeDTO);
    TreeDTO getTree(Long treeId);
    void deleteTree(TreeDTO treeDTO);
}

