package com.company.receipt;

import com.company.receipt.components.*;
import com.company.utils.InputReader;
import com.company.utils.Response;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ReceiptFormHandler {
    private final ReceiptService service;

    public ReceiptFormHandler(ReceiptService service) {
        this.service = service;
    }

    public void start() {
        while (true) {
            printMainOptions();
            String option = InputReader.readLine();
            switch (option) {
                case "1":
                    Response res = fillReceiptForm();
                    System.out.println(res.getMsg());
                    if (res.isSuccessful()) {
                        System.out.println("Enter yes if you want to see the Receipt Summary");
                        if ("yes".equalsIgnoreCase(InputReader.readLine())) {
                            System.out.println(service.getReceipt().getMsg());
                        }
                    }
                    break;
                case "2":
                    System.out.println(service.getReceipt().getMsg());
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private void printMainOptions() {
        System.out.printf("Choose an option%n" +
                "1) Fill receipt form%n2) Print receipt form%n3) Exit%nOption number: ");
    }

    public Response fillReceiptForm() {
        Client client = new Client();
        CI ci = new CI();
        PaymentDetail paymentDetail = new PaymentDetail();
        ProductDetail productDetail = new ProductDetail();
        Receipt receipt = new Receipt();

        System.out.println("Filling new receipt");

        // Client Data
        client.setFullName(readString("Full Name", "fullName", Client.class));
        ci.setNumber(readInt("CI Number", "number", CI.class));
        ci.setExtension((CIExtension) InputReader.readEnumFor("CI extension", CIExtension.class));
        client.setEmail(readString("Email", "email", Client.class));
        client.setAddress(readString("Address", "address", Client.class));

        // Payment Detail
        paymentDetail.setPaymentMethod(
                (PaymentMethod) InputReader.readEnumFor("Payment method", PaymentMethod.class));
        if (paymentDetail.getPaymentMethod() == PaymentMethod.CREDIT_CARD) {
            paymentDetail.setCreditCardNumber(readString(
                    "Credit card number", "creditCardNumber", PaymentDetail.class));
        }

        // Product Detail
        productDetail.setDescription(readString(
                "Product description", "description", ProductDetail.class));
        productDetail.setQuantity(readInt(
                "Product quantity", "quantity", ProductDetail.class));
        productDetail.setUnitPrice(readDouble(
                "Product unit price", "unitPrice", ProductDetail.class));
        productDetail.setCurrency((Currency) InputReader.readEnumFor("Currency", Currency.class));

        client.setCi(ci);
        receipt.setClient(client);
        receipt.setPaymentDetail(paymentDetail);
        receipt.setProductDetail(productDetail);

        return service.saveReceipt(receipt);
    }

    private String readString(String name, String propertyName, Class<?> ownerClazz) {
        Set<? extends ConstraintViolation<?>> violations;
        while (true) {
            String line = InputReader.readLineFor(name);
            violations = service.validateValue(ownerClazz, propertyName, line);
            if (violations.isEmpty()) {
                return line;
            }
            printConstraintViolations(violations);
        }
    }

    private int readInt(String name, String propertyName, Class<?> ownerClazz) {
        Set<? extends ConstraintViolation<?>> violations;
        while (true) {
            int number = InputReader.readIntFor(name);
            violations = service.validateValue(ownerClazz, propertyName, number);
            if (violations.isEmpty()) {
                return number;
            }
            printConstraintViolations(violations);
        }
    }

    private double readDouble(String name, String propertyName, Class<?> ownerClazz) {
        Set<? extends ConstraintViolation<?>> violations;
        while (true) {
            double number = InputReader.readDoubleFor(name);
            violations = service.validateValue(ownerClazz, propertyName, number);
            if (violations.isEmpty()) {
                return number;
            }
            printConstraintViolations(violations);
        }
    }

    private void printConstraintViolations(Set<? extends ConstraintViolation<?>> violations) {
        for (ConstraintViolation<?> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}
