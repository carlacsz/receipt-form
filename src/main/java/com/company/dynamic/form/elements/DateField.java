package com.company.dynamic.form.elements;

import java.util.Date;

public class DateField extends FormElement<Date> {
    @Override
    public String showValueOptions() {
        return "dd-mm-yyyy";
    }
}
