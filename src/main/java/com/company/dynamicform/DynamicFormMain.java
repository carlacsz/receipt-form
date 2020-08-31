package com.company.dynamicform;

import com.company.utils.InputReader;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamicFormMain {
    public static final Logger LOG = Logger.getLogger("com.company");

    public static void main(String[] args) {
        LOG.setLevel(Level.OFF);
        DynamicFormHandler handler = new DynamicFormHandler(new DynamicFormService(), new InputReader());
        handler.start();
    }
}
