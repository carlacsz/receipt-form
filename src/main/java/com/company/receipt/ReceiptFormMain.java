package com.company.receipt;

import com.company.utils.JsonSerializer;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiptFormMain {
    public static final Logger H_LOGGER = Logger.getLogger("org.hibernate");
    public static final Logger LOGGER = Logger.getLogger("com.company");

    public static void main(String[] args) {
        H_LOGGER.setLevel(Level.OFF);
        LOGGER.setLevel(Level.OFF);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        ReceiptService service = new ReceiptService(new JsonSerializer(), validator);
        ReceiptFormHandler handler = new ReceiptFormHandler(service);
        handler.start();
    }
}
