package com.company;

import com.company.components.*;
import com.company.utils.Encryptor;
import com.company.utils.JsonFileSerializer;
import com.company.utils.Response;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Date;
import java.util.Set;

public class ReceiptService {
    public static final String filePath = "src/data/receipt.json";
    private final Validator validator;

    public ReceiptService(Validator validator) {
        this.validator = validator;
    }

    public Response saveReceipt(Receipt receipt) {
        if (!isValid(receipt)) {
            return new Response(false, validate(receipt).toString());
        }
        receipt.setCreatedDate(new Date());
        receipt.getProductDetail().calculateTotalPrice();
        PaymentDetail paymentDetail = receipt.getPaymentDetail();
        String cardNumber = paymentDetail.getCreditCardNumber();
        if (cardNumber != null) {
            String encryptedCardNumber = Encryptor.encrypt(cardNumber);
            paymentDetail.setCreditCardNumber(encryptedCardNumber);
        }
        Receipt savedReceipt = (Receipt) JsonFileSerializer.write(filePath, receipt);
        if (savedReceipt == null) {
            return new Response(false, "Receipt could not be saved");
        }
        return new Response(true, "Receipt was stored successfully!");
    }

    public Response getReceipt() {
        Receipt receipt = (Receipt) JsonFileSerializer.read(filePath, Receipt.class);
        if (receipt == null) {
            return new Response(false, "Receipt not found!");
        }
        PaymentDetail paymentDetail = receipt.getPaymentDetail();
        String cardNumber = paymentDetail.getCreditCardNumber();
        if (cardNumber != null) {
            String decryptedCardNumber = Encryptor.decrypt(cardNumber);
            paymentDetail.setCreditCardNumber(decryptedCardNumber);
        }
        return new Response(true, receipt.toString());
    }

    public Set<ConstraintViolation<Object>> validateValue(Class clazz, String propertyName, Object value) {
        return validator.validateValue(clazz, propertyName, value);
    }

    private boolean isValid(Receipt receipt) {
        return validator.validate(receipt).isEmpty();
    }

    public Set<ConstraintViolation<Receipt>> validate(Receipt receipt) {
        return validator.validate(receipt);
    }
}
