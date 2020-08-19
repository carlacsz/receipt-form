package com.company.dynamic.form.elements;

public class RadioButton extends OptionList {
    @Override
    public String showValueOptions() {
        StringBuilder valueOptions = new StringBuilder();
        int start = 1;
        for (String option : getOptions()) {
            valueOptions.append(start).append(") ")
                    .append(option).append(" ");
            start++;
        }
        valueOptions.append('\n');
        return valueOptions.toString();
    }
}
