package com.company.dynamic.form.elements;

public class TextPassword extends Text {
    @Override
    public String toString() {
        return getName() + ": ********";
    }
}
