package com.company.components;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt {
    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm a";
    
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
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateFormatted =  dateFormat.format(createdDate);
        return "Receipt\n" +
                "   Create Date        " + dateFormatted + '\n' +
                "   Buyer Details\n" + client + paymentDetail +
                "   Product Details\n" + productDetail;
    }
}
