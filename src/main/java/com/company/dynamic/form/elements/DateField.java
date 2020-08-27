package com.company.dynamic.form.elements;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateField extends FormElement<Date> {
    private static final String format = "dd/MM/yyyy";

    @Override
    public void defineValue(String value) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            setValue(dateFormat.parse(value));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String showValueOptions() {
        return "Use the format " + format + '\n' + getName() + ": ";
    }

    @Override
    public List<String> validateValue(String value) {
        List<String> violations = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            dateFormat.parse(value);
        } catch (ParseException e) {
            violations.add("Invalid date format, use the format " + format);
        }
        return violations;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return getName() + ": " + dateFormat.format(getValue());
    }
}
