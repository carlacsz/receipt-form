package com.company.dynamic.form.elements;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, property = "@class")
public abstract class FormElement<T> {
    private String name;
    private T value;

    public abstract void defineValue(String value);

    public String showValueOptions() {
        return "";
    }

    public List<String> validateValue(String value) {
        return new ArrayList<>();
    }

    public void fillValidations(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    protected void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + ": " + value;
    }
}
