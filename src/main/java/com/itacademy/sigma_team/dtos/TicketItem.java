package com.itacademy.sigma_team.dtos;

sealed public interface TicketItem permits DecorationDTO, FlowerDTO, TreeDTO {}
