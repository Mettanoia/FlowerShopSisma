package com.itacademy.sigma_team.dtos;

import com.itacademy.sigma_team.domain.Tree;

sealed public interface TicketItem permits Tree, DecorationDTO, FlowerDTO, TreeDTO {}
