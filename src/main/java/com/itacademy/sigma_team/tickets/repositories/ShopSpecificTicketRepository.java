package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.tickets.use_cases.TicketGateway;
import com.itacademy.sigma_team.flower_shop.repositories.ShopRepository;

import java.util.Collection;
import java.util.function.Supplier;

public final class ShopSpecificTicketRepository implements TicketGateway {

    private static ShopSpecificTicketRepository instance;
    private static Supplier<String> shopNameSupplier;
    private final TicketSqlRepository ticketSqlRepository;
    private final ShopRepository shopRepository;
    private static String shopName;

    public ShopSpecificTicketRepository() {
        this.ticketSqlRepository = new TicketSqlRepository();
        this.shopRepository = new ShopRepository();
    }

    public static ShopSpecificTicketRepository getInstance() {
        if (instance == null) {
            instance = new ShopSpecificTicketRepository();
        }
        return instance;
    }

    public static void setShopNameSupplier(Supplier<String> supplier) {
        if (shopNameSupplier == null) {
            shopNameSupplier = supplier;
        }
    }

    private String getShopName() {
        if (shopName == null && shopNameSupplier != null) {
            shopName = shopNameSupplier.get();
        }
        if (shopName == null) {
            throw new IllegalStateException("Shop name has not been set.");
        }
        return shopName;
    }

    @Override
    public void add(TicketDTO dto) {
        ticketSqlRepository.add(dto, getShopName());
    }

    @Override
    public void delete(TicketDTO dto) {
        ticketSqlRepository.delete(dto);
    }

    @Override
    public TicketDTO get(String id) {
        return ticketSqlRepository.get(id);
    }

    @Override
    public Collection<TicketDTO> getAll() {
        return ticketSqlRepository.getAll(getShopName());
    }
}
