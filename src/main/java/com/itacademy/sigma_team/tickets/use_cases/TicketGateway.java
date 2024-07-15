package com.itacademy.sigma_team.tickets.use_cases;

import com.itacademy.sigma_team.gateway_mixins.writers.Adder;
import com.itacademy.sigma_team.gateway_mixins.writers.Deleter;
import com.itacademy.sigma_team.gateway_mixins.readers.Reader;
import com.itacademy.sigma_team.tickets.repositories.TicketDTO;

public interface TicketGateway extends Adder<TicketDTO>, Deleter<TicketDTO>, Reader<TicketDTO> { }
