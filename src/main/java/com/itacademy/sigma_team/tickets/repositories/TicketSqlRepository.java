package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.tickets.use_cases.TicketGateway;

import java.io.IOException;

public final class TicketSqlRepository implements TicketGateway {

    @Override
    public void add(TicketDTO dto) throws IOException {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    public void delete(TicketDTO dto) throws IOException {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    public TicketDTO get(String id) {
        return null;
    }
}
