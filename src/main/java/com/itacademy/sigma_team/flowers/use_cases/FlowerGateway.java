package com.itacademy.sigma_team.flowers.use_cases;

import com.itacademy.sigma_team.flowers.repositories.FlowerDTO;
import com.itacademy.sigma_team.gateways.readers.Adder;
import com.itacademy.sigma_team.gateways.readers.Deleter;
import com.itacademy.sigma_team.gateways.readers.Reader;

public interface FlowerGateway extends Adder<FlowerDTO>, Reader<FlowerDTO>, Deleter<FlowerDTO> { }
