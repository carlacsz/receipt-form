package com.company.components;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ProductDetail implements Serializable {

    @NotBlank(message = "Description for product must not be empty")
    @Size(min = 2, max = 100, message = "Description for product must be between 2 and 100 characters long")
    private String description;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    private double unitPrice;
    private Currency currency;
    private double total;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getTotal() {
        return total;
    }

    public void calculateTotalPrice() {
        total = quantity * unitPrice;
    }

    @Override
    public String toString() {
        return "       Product Description        " + description + '\n' +
                "       Quantity                   " + quantity + '\n' +
                "       Unit Price                 " + unitPrice + '\n' +
                "       Currency                   " + currency + '\n' +
                "       Total                      " + total + ' '+ currency + '\n';
    }
}
