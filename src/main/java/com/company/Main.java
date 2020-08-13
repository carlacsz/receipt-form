package com.company;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static final Logger H_LOGGER = Logger.getLogger("org.hibernate");

    public static void main(String[] args) {
        H_LOGGER.setLevel(Level.OFF);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        ReceiptService service = new ReceiptService(validator);
        FormHandler handler = new FormHandler(service);
        handler.start();
    }
}