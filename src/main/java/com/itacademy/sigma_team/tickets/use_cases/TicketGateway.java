package com.itacademy.sigma_team.tickets.use_cases;

import com.itacademy.sigma_team.gateways.readers.Adder;
import com.itacademy.sigma_team.gateways.readers.Deleter;
import com.itacademy.sigma_team.tickets.repositories.TicketDTO;

public interface TicketGateway extends Adder<TicketDTO>, Deleter<TicketDTO> { }
