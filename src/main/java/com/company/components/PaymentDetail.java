package com.company.components;

import javax.validation.constraints.Pattern;

public class PaymentDetail {

    private PaymentMethod paymentMethod;

    @Pattern(regexp = "\\d{16}", message = "Credit card number must be a 16 digit number")
    private String creditCardNumber;

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Override
    public String toString() {
        return "       Payment Method        " + paymentMethod.getName() + '\n' +
                "       Credit Card Number    " + transformCreditNumber() + '\n';
    }

    private String transformCreditNumber() {
        if (creditCardNumber == null) {
            return "-";
        }
        return "xxxx-xxxx-xxxx-" +
                creditCardNumber.substring(12);
    }
}
