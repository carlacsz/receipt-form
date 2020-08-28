package com.company.dynamic.form.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextNumber extends Text {
    public TextNumber() {
        setPatternStr("^-?[0-9]\\d*(\\.\\d*)?$");
    }

    @Override
    public List<String> validateValue(String value) {
        List<String> violations = new ArrayList<>();

        Pattern pattern = Pattern.compile(getPatternStr());
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            violations.add("Not a valid number");
        } else {
            if (getMin() != null && Double.parseDouble(value) < getMin()) {
                violations.add(String.format("%s has to be greater than %s",
                        getName(), getMin()));
            }
            if (getMax() != null && Double.parseDouble(value) > getMax()) {
                violations.add(String.format("%s has to be less than %s",
                        getName(), getMax()));
            }
        }
        return violations;
    }

    @Override
    public void fillValidations() {
        this.fillRangeValidations();
    }
}
