package com.company.dynamic.form.elements;

import com.company.utils.InputReader;

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
    public List<String> validateValue(String value) {
        List<String> violations = new ArrayList<>();
        try {
            options.get(Integer.parseInt(value) - 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            violations.add("Not a valid option");
        }
        return violations;
    }

    @Override
    public void fillValidations() {
        while (options.size() == 0) {
            options = InputReader.readMultipleLinesFor(this.getClass().getSimpleName());
            if (options.size() == 0) {
                System.out.println(this.getClass().getSimpleName() + " must have at least one option");
            }
        }
    }
}
