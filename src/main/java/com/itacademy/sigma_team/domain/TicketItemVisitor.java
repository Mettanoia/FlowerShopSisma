package com.itacademy.sigma_team.domain;

public interface TicketItemVisitor {
    void visit(Flower flower);
    void visit(Tree tree);
    void visit(Decoration decoration);
}
