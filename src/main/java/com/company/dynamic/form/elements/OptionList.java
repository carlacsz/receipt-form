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

    @Override
    public void defineValue(String value) {
        setValue(options.get(Integer.parseInt(value) - 1));
    }

    @Override
    public String showValueOptions() {
        StringBuilder valueOptions = new StringBuilder();
        int start = 1;
        valueOptions.append('\n');
        for (String option : options) {
            valueOptions.append(start).append(") ")
                    .append(option).append("\n");
            start++;
        }
        valueOptions.append("Option: ");
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

}
