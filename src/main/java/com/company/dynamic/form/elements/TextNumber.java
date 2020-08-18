package com.company.dynamic.form.elements;

public class TextNumber extends Text {
    public TextNumber() {
        setPattern("^-?[0-9]\\d*(\\.\\d*)?$");
    }

}
