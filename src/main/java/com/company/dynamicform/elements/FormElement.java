package com.company.dynamicform.elements;

import com.company.utils.IReader;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, property = "@class")
public abstract class FormElement<T> {
    protected String name;
    protected T value;

    public abstract void defineValue(String input) throws Exception;

    public String showValueOptions() {
        return "";
    }

    public List<String> validate(String input) {
        return new ArrayList<>();
    }

    public void fillValidations(IReader reader){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + ": " + value;
    }
}
