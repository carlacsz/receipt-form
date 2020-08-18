package com.company.dynamic.form.elements;

public class Text extends FormElement<String> {
    private String pattern;
    private Integer min = 0;
    private Integer max;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

}
