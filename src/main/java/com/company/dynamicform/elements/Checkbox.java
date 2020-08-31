package com.company.dynamicform.elements;

import java.util.ArrayList;
import java.util.List;

public class Checkbox extends FormElement<Boolean> {

    @Override
    public void defineValue(String input) {
        value = Boolean.parseBoolean(input);
    }

    @Override
    public String showValueOptions() {
        return "\nTrue or False: ";
    }

    @Override
    public List<String> validate(String input) {
        List<String> violations = new ArrayList<>();
        if (!"true".equalsIgnoreCase(input) && !"false".equalsIgnoreCase(input)) {
            violations.add("Invalid value, should be true or false");
        }
        return violations;
    }

    @Override
    public String toString() {
        String checked = value ? "Yes" : "No";
        return getName() + ": " + checked;
    }
}
