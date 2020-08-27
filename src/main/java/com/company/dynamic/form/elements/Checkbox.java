package com.company.dynamic.form.elements;

import java.util.ArrayList;
import java.util.List;

public class Checkbox extends FormElement<Boolean> {

    @Override
    public void defineValue(String value) {
        setValue(Boolean.parseBoolean(value));
    }

    @Override
    public String showValueOptions() {
        return "\nTrue or False: ";
    }

    @Override
    public List<String> validateValue(String value) {
        List<String> violations = new ArrayList<>();
        if (!"true".equalsIgnoreCase(value) && !"false".equalsIgnoreCase(value)) {
            violations.add("Invalid value, should be true or false");
        }
        return violations;
    }

    @Override
    public String toString() {
        String checked = getValue() ? "Yes" : "No";
        return getName() + ": " + checked;
    }
}
