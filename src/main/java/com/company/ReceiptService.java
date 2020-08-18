package com.company;

import com.company.components.*;
import com.company.utils.Encryptor;
import com.company.utils.FileSerializer;
import com.company.utils.JsonFileSerializer;
import com.company.utils.Response;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

public class ReceiptService {
    public static final String filePath = "src/data/receipt.json";
    private final Validator validator;
    private final FileSerializer serializer;

    public ReceiptService(FileSerializer serializer, Validator validator) {
        this.serializer = serializer;
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
        try {
            if (cardNumber != null) {
                String encryptedCardNumber = Encryptor.encrypt(cardNumber);
                paymentDetail.setCreditCardNumber(encryptedCardNumber);
            }
            serializer.write(filePath, receipt);
            return new Response(true, "Receipt was stored successfully!");
        } catch (IOException e) {
            return new Response(false,
                    "Receipt could not be saved. Serialization failed!\n" + e.getMessage());
        } catch (Exception e) {
            return new Response(false,
                    "Receipt could not be saved. Encryption failed!");
        }
    }

    public Response getReceipt() {
        try {
            Receipt receipt = serializer.read(filePath, Receipt.class);
            PaymentDetail paymentDetail = receipt.getPaymentDetail();
            String cardNumber = paymentDetail.getCreditCardNumber();
            if (cardNumber != null) {
                String decryptedCardNumber = Encryptor.decrypt(cardNumber);
                paymentDetail.setCreditCardNumber(decryptedCardNumber);
            }
            return new Response(true, receipt.toString());
        } catch (IOException e) {
            return new Response(false,
                    "Receipt could not be loaded. Deserialization failed!\n" + e.getMessage());
        } catch (Exception e) {
            return new Response(false,
                    "Receipt could not be loaded. Decryption failed!");
        }
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
