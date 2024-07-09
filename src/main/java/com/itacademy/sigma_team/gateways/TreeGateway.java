package com.itacademy.sigma_team.gateways;


import com.itacademy.sigma_team.trees.repositories.TreeDTO;

public interface TreeGateway {
    void addTree(TreeDTO treeDTO);


    TreeDTO getTree(String treeId);



    void deleteTree(TreeDTO treeDTO);
}

