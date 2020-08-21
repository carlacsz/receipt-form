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
    public List<String> validate(String str) {
        List<String> violations = new ArrayList<>();

        Pattern pattern = Pattern.compile(getPatternStr());
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            violations.add("Not a valid number");
        } else {
            if (getMin() != null && Double.parseDouble(str) < getMin()) {
                violations.add(String.format("%s has to be greater than %s",
                        getName(), getMin()));
            }
            if (getMax() != null && Double.parseDouble(str) > getMax()) {
                violations.add(String.format("%s has to be less than %s",
                        getName(), getMax()));
            }
        }
        return violations;
    }
}
