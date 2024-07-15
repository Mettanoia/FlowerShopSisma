package com.itacademy.sigma_team.decorations.use_cases;

import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.gateway_mixins.writers.Adder;
import com.itacademy.sigma_team.gateway_mixins.writers.Deleter;
import com.itacademy.sigma_team.gateway_mixins.readers.Reader;

public interface DecorationGateway extends Adder<DecorationDTO>, Reader<DecorationDTO>, Deleter<DecorationDTO> {
}
