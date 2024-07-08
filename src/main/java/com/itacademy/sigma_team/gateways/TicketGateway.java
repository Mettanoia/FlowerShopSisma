package com.itacademy.sigma_team.gateways;

import com.itacademy.sigma_team.gateways.readers.Adder;
import com.itacademy.sigma_team.gateways.readers.Deleter;
import com.itacademy.sigma_team.tickets.repositories.TicketDTO;

interface TicketGateway extends Adder<TicketDTO>, Deleter<TicketDTO> { }
