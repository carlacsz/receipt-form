package com.company.dynamicform.elements;

public class RadioButton extends OptionList {
    @Override
    public String showValueOptions() {
        StringBuilder valueOptions = new StringBuilder();
        int start = 1;
        valueOptions.append('\n');
        for (String option : getOptions()) {
            valueOptions.append(start).append(") ")
                    .append(option).append(" ");
            start++;
        }
        valueOptions.append("\nOption: ");
        return valueOptions.toString();
    }
}
