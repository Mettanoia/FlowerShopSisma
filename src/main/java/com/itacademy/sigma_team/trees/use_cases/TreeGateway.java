package com.itacademy.sigma_team.trees.use_cases;


import com.itacademy.sigma_team.dtos.TreeDTO;
import com.itacademy.sigma_team.gateway_mixins.writers.Adder;
import com.itacademy.sigma_team.gateway_mixins.writers.Deleter;
import com.itacademy.sigma_team.gateway_mixins.readers.Reader;

public interface TreeGateway extends Adder<TreeDTO>, Reader<TreeDTO>, Deleter<TreeDTO> {

}

