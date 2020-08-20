package com.company.dynamic.form.elements;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateField extends FormElement {
    private static final String format = "dd/MM/yyyy";

    @Override
    public String showValueOptions() {
        return "Use the format " + format + '\n' + getName() + ": ";
    }

    @Override
    public List<String> validate(String str) {
        List<String> violations = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            dateFormat.parse(str);
        } catch (ParseException e) {
            violations.add("Invalid date format, use the format " + format);
        }
        return violations;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            Date date = dateFormat.parse(getValue());
            return getName() + ": " + dateFormat.format(date);
        } catch (ParseException e) {
            return getName() + ": " + getValue();
        }
    }
}
