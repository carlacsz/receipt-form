package com.company.dynamic.form.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Text extends FormElement<String> {
    private String patternStr;
    private Integer min;
    private Integer max;

    public String getPatternStr() {
        return patternStr;
    }

    public void setPatternStr(String patternStr) {
        this.patternStr = patternStr;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    @Override
    public void defineValue(String value) {
        setValue(value);
    }

    @Override
    public List<String> validateValue(String value) {
        List<String> violations = new ArrayList<>();
        if (min != null && value.length() < min) {
            violations.add(String.format("%s has to be at least %s characters",
                    getName(), min));
        }
        if (max != null && value.length() > max) {
            violations.add(String.format("%s has to be max %s characters long",
                    getName(), max));
        }
        if (patternStr != null) {
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(value);
            if (!matcher.matches()) {
                violations.add(String.format("%s tiene que corresponder a la expresión regular \"%s\"",
                        getName(), patternStr));
            }
        }
        return violations;
    }
}
