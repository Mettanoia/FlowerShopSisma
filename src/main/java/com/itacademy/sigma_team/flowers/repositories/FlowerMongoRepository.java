package com.itacademy.sigma_team.flowers.repositories;


import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

final class FlowerMongoRepository implements FlowerGateway {

    @Override
    public void add(FlowerDTO dto) throws IOException {

    }

    @Override
    public void delete(FlowerDTO dto) throws IOException {

    }

    @Override
    public FlowerDTO get(String id) {
        return null;
    }

    @Override
    public Collection<FlowerDTO> getAll() throws IOException {
        throw new UnsupportedOperationException();
    }

}
