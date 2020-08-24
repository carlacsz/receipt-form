package com.company.dynamic.form;

import com.company.dynamic.form.elements.FormElement;

import java.util.HashMap;
import java.util.Map;

public class FormElementFactory {
    private static final Map<String, FormElement<?>> formElemInstances = new HashMap<>();

    public static void register(String optionNumber, FormElement<?> instance) {
        if (optionNumber != null && instance != null) {
            formElemInstances.put(optionNumber, instance);
        }
    }

    public static FormElement<?> getInstance(String optionNumber) {
        return formElemInstances.get(optionNumber);
    }

    public static Map<String, FormElement<?>> getFormElemInstances() {
        return formElemInstances;
    }
}
