package com.company.dynamic.form.elements;

public class Checkbox extends FormElement<Boolean> {

    @Override
    public String showValueOptions() {
        return "True or False";
    }
}
