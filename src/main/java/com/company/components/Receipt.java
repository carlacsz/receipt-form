package com.company.components;

import com.company.utils.DateFormatter;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt {

    private Date createdDate;

    @Valid
    private Client client;

    @Valid
    private PaymentDetail paymentDetail;

    @Valid
    private ProductDetail productDetail;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public PaymentDetail getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public String toString() {
        return "Receipt\n" +
                "   Create Date        " + DateFormatter.format(createdDate, "dd/MM/yyyy HH:mm a") + '\n' +
                "   Buyer Details\n" + client + paymentDetail +
                "   Product Details\n" + productDetail;
    }
}
