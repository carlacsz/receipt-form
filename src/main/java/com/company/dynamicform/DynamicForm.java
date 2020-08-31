package com.company.dynamicform;

import com.company.dynamicform.elements.FormElement;

import java.util.ArrayList;
import java.util.List;

public class DynamicForm {
    private String name = "";
    private boolean filled = false;
    private List<FormElement<?>> formElements = new ArrayList<>();

    public void addFormElement(FormElement<?> formElement) {
        this.formElements.add(formElement);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public List<FormElement<?>> getFormElements() {
        return formElements;
    }

}
