package com.company.components;

public enum PaymentMethod {
    CASH("Cash"),
    CREDIT_CARD("Credit Card");

    private final String name;

    PaymentMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
