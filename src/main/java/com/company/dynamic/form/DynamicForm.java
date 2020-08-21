package com.company.dynamic.form;

import com.company.dynamic.form.elements.FormElement;

import java.util.ArrayList;
import java.util.List;

public class DynamicForm {
    private String name = "";
    private boolean filled = false;
    private List<FormElement<?>> formElements = new ArrayList<>();

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

    public void setFormElements(List<FormElement<?>> formElements) {
        this.formElements = formElements;
    }

    public void addFormElement(FormElement<?> formElement) {
        this.formElements.add(formElement);
    }
}
