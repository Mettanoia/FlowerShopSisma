package com.itacademy.sigma_team.repositories_factories;

import com.itacademy.sigma_team.tickets.repositories.TicketSqlRepository;
import com.itacademy.sigma_team.tickets.use_cases.TicketGateway;

public final class TicketRepositoryFactory {

    public static TicketGateway getInstance() {return new TicketSqlRepository();}

}
