package com.company.dynamic.form.elements;

import java.util.ArrayList;
import java.util.List;

public abstract class OptionList extends FormElement<String> {
    private List<String> options = new ArrayList<>();

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void addOption(String option) {
        options.add(option);
    }

    @Override
    public String showValueOptions() {
        StringBuilder valueOptions = new StringBuilder();
        int start = 1;
        for (String option : options) {
            valueOptions.append(start).append(") ")
                    .append(option).append("\n");
            start++;
        }
        return valueOptions.toString();
    }

    @Override
    public List<String> validate(String str) {
        List<String> violations = new ArrayList<>();
        try {
            options.get(Integer.parseInt(str) - 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            violations.add("Not a valid option");
        }
        return violations;
    }

    @Override
    public String toString() {
        return getName() + ": " + options.get(Integer.parseInt(getValue()) - 1);
    }
}
