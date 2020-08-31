package com.company.dynamicform.elements;

import com.company.utils.IReader;
import com.company.utils.InputReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Text extends FormElement<String> {
    protected String patternStr;
    protected Integer min;
    protected Integer max;

    public String getPatternStr() {
        return patternStr;
    }

    public void setPatternStr(String patternStr) {
        this.patternStr = patternStr;
    }

    @Override
    public void defineValue(String input) throws Exception {
        value = input;
    }

    @Override
    public List<String> validate(String input) {
        List<String> violations = new ArrayList<>();
        if (min != null && input.length() < min) {
            violations.add(String.format("%s has to be at least %s characters",
                    name, min));
        }
        if (max != null && input.length() > max) {
            violations.add(String.format("%s has to be max %s characters long",
                    name, max));
        }
        if (patternStr != null) {
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(input);
            if (!matcher.matches()) {
                violations.add(String.format("%s has to correspond to the regular expression \"%s\"",
                        name, patternStr));
            }
        }
        return violations;
    }

    @Override
    public void fillValidations(IReader reader) {
        if (reader.wantsToAddValueFor("Pattern validation"))
            patternStr = reader.readPatternFor(getName());
        fillRangeValidations(reader);
    }

    protected void fillRangeValidations(IReader reader) {
        if (reader.wantsToAddValueFor("Min validation"))
            min = reader.readIntFor("Min value");
        if (reader.wantsToAddValueFor("Max validation"))
            max = reader.readIntFor("Max value");
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }
}
