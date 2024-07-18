package com.itacademy.sigma_team.dtos;

import java.lang.reflect.InvocationTargetException;

sealed public interface TicketItem permits DecorationDTO, FlowerDTO, TreeDTO {

    static String getId(TicketItem ticketItem) {
        try {
            return ticketItem.getClass().getMethod("id").invoke(ticketItem).toString();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    static String getName(TicketItem ticketItem) {
        try {
            return ticketItem.getClass().getMethod("name").invoke(ticketItem).toString();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    static double getPrice(TicketItem ticketItem) {
        try {
            return (double) ticketItem.getClass().getMethod("price").invoke(ticketItem);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    static int getStock(TicketItem ticketItem) {
        try {
            return (int) ticketItem.getClass().getMethod("stock").invoke(ticketItem);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

}
