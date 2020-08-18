package com.company;

import com.company.utils.JsonFileSerializer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamicFormMain {
    public static final Logger H_LOGGER = Logger.getLogger("org.hibernate");
    public static final Logger LOG = Logger.getLogger("com.company");

    public static void main(String[] args) {
        H_LOGGER.setLevel(Level.OFF);
        LOG.setLevel(Level.WARNING);

        DynamicFormHandler handler = new DynamicFormHandler(new JsonFileSerializer());
        handler.start();

    }
}
