package com.itacademy.sigma_team.domain;

public class TicketItemVisitorNaiveImpl implements TicketItemVisitor{

    @Override
    public void visit(Flower flower) { throw new UnsupportedOperationException("Not yet implemented."); }

    @Override
    public void visit(Tree tree) { throw new UnsupportedOperationException("Not yet implemented."); }

    @Override
    public void visit(Decoration decoration) { throw new UnsupportedOperationException("Not yet implemented."); }
}
