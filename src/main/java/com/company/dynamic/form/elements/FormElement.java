package com.company.dynamic.form.elements;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, property = "@class")
public abstract class FormElement {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String showValueOptions() {
        return "";
    }

    public List<String> validate(String str) {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return name + ": " + value;
    }
}
