package com.itacademy.sigma_team.flowers.use_cases;

import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.gateway_mixins.writers.Adder;
import com.itacademy.sigma_team.gateway_mixins.writers.Deleter;
import com.itacademy.sigma_team.gateway_mixins.readers.Reader;

public interface FlowerGateway extends Adder<FlowerDTO>, Reader<FlowerDTO>, Deleter<FlowerDTO> { }
