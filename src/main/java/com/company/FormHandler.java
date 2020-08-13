package com.company;


import com.company.components.*;
import com.company.utils.Response;

import javax.validation.ConstraintViolation;
import java.util.Scanner;
import java.util.Set;

public class FormHandler {
    private final Scanner scanner = new Scanner(System.in);
    private final ReceiptService service;

    public FormHandler(ReceiptService service) {
        this.service = service;
    }

    public void start() {
        while (true) {
            printMainOptions();
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    Response res = fillReceiptForm();
                    System.out.println(res.getMsg());
                    if (res.isSuccessful()) {
                        System.out.println("Enter yes if you want to see the Receipt Summary");
                        if ("yes".equalsIgnoreCase(scanner.nextLine())) {
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
        System.out.printf("Choose an option%n1) Fill receipt form%n2) Print receipt form%n3) Exit%n");
    }

    public Response fillReceiptForm() {
        Client client = new Client();
        CI ci = new CI();

        PaymentDetail paymentDetail = new PaymentDetail();
        ProductDetail productDetail = new ProductDetail();

        Receipt receipt = new Receipt();
        receipt.setClient(client);

        Set<? extends ConstraintViolation<?>> violations;

        System.out.println("Filling new receipt");

        // Client Data
        while (true) {
            System.out.println("Enter Full Name:");
            client.setFullName(readLine());
            violations = service.validateProperty(client, "fullName");
            if (violations.isEmpty()) {
                break;
            }
            printViolations(violations);
        }
        while (true) {
            System.out.println("Enter CI Number:");
            ci.setNumber(readInt());
            violations = service.validateProperty(ci, "number");
            if (violations.isEmpty()) {
                break;
            }
            printViolations(violations);
        }
        while (true) {
            System.out.println("Choose a CI extension:");
            printEnumOptions(CIExtension.values());
            try {
                int option = readInt();
                CIExtension ext = CIExtension.values()[option - 1];
                ci.setExtension(ext);
                break;
            } catch (Exception e) {
                System.out.println("Invalid option");
            }
        }
        while (true) {
            System.out.println("Enter email:");
            client.setEmail(readLine());
            violations = service.validateProperty(client, "email");
            if (violations.isEmpty()) {
                break;
            }
            printViolations(violations);
        }
        while (true) {
            System.out.println("Enter address");
            client.setAddress(readLine());
            violations = service.validateProperty(client, "address");
            if (violations.isEmpty()) {
                break;
            }
            printViolations(violations);
        }
        client.setCi(ci);

        // Payment Detail
        while (true) {
            System.out.println("Choose a payment method:");
            printEnumOptions(PaymentMethod.values());
            try {
                PaymentMethod method = PaymentMethod.values()[readInt() - 1];
                paymentDetail.setPaymentMethod(method);
                break;
            } catch (Exception e) {
                System.out.println("Invalid option");
            }
        }
        if (paymentDetail.getPaymentMethod() == PaymentMethod.CREDIT_CARD) {
            while (true) {
                System.out.println("Enter Credit Number:");
                paymentDetail.setCreditCardNumber(readLine());
                violations = service.validateProperty(paymentDetail, "creditCardNumber");
                if (violations.isEmpty()) {
                    break;
                }
                printViolations(violations);
            }
        }

        // Product Detail
        while (true) {
            System.out.println("Enter product description:");
            productDetail.setDescription(readLine());
            violations = service.validateProperty(productDetail, "description");
            if (violations.isEmpty()) {
                break;
            }
            printViolations(violations);
        }
        while (true) {
            System.out.println("Enter product quantity:");
            productDetail.setQuantity(readInt());
            violations = service.validateProperty(productDetail, "quantity");
            if (violations.isEmpty()) {
                break;
            }
            printViolations(violations);
        }
        while (true) {
            System.out.println("Enter product unit price:");
            productDetail.setUnitPrice(readDouble());
            violations = service.validateProperty(productDetail, "unitPrice");
            if (violations.isEmpty()) {
                break;
            }
            printViolations(violations);
        }
        while (true) {
            System.out.println("Choose a currency:");
            printEnumOptions(Currency.values());
            try {
                Currency currency = Currency.values()[readInt() - 1];
                productDetail.setCurrency(currency);
                break;
            } catch (Exception e) {
                System.out.println("Invalid option");
            }
        }

        receipt.setClient(client);
        receipt.setPaymentDetail(paymentDetail);
        receipt.setProductDetail(productDetail);

        return service.saveReceipt(receipt);
    }

    private void printEnumOptions(Enum[] options) {
        for (Enum option : options) {
            System.out.printf("%d) %s%n", option.ordinal() + 1, option.name());
        }
    }

    private void printViolations(Set<? extends ConstraintViolation<?>> violations) {
        for (ConstraintViolation<?> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    private String readLine() {
        return scanner.nextLine();
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(readLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format, try again!");
            }
        }
    }

    private Double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(readLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format, try again!");
            }
        }
    }

}
