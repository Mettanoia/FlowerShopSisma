package com.itacademy.sigma_team.abstract_shop;

public abstract class ShopAbstract {

    protected abstract static class Builder<T extends Builder<T>> {

        protected abstract T self();

        protected abstract ShopAbstract build();

    }

}
