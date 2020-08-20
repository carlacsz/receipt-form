package com.company.dynamic.form.elements;

import java.util.ArrayList;
import java.util.List;

public class Checkbox extends FormElement {

    @Override
    public String showValueOptions() {
        return "\nTrue or False: ";
    }

    @Override
    public List<String> validate(String str) {
        List<String> violations = new ArrayList<>();
        if (!"true".equalsIgnoreCase(str) && !"false".equalsIgnoreCase(str)) {
            violations.add("Invalid value, should be true or false");
        }
        return violations;
    }

    @Override
    public String toString() {
        String checked = Boolean.parseBoolean(getValue()) ? "Yes" : "No";
        return getName() + ": " + checked;
    }
}
