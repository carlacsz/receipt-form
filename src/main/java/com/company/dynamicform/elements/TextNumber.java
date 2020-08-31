package com.company.dynamicform.elements;

import com.company.utils.IReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextNumber extends Text {
    public TextNumber() {
        setPatternStr("^-?[0-9]\\d*(\\.\\d*)?$");
    }

    @Override
    public List<String> validate(String input) {
        List<String> violations = new ArrayList<>();

        Pattern pattern = Pattern.compile(getPatternStr());
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            violations.add("Not a valid number");
        } else {
            if (min != null && Double.parseDouble(input) < min) {
                violations.add(name + " has to be greater than " + min);
            }
            if (max != null && Double.parseDouble(input) > max) {
                violations.add(name + " has to be less than " + max);
            }
        }
        return violations;
    }

    @Override
    public void fillValidations(IReader reader) {
        this.fillRangeValidations(reader);
    }
}
