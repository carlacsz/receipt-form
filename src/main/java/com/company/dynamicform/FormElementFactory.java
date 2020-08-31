package com.company.dynamicform;

import com.company.dynamicform.elements.FormElement;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class FormElementFactory {
    private static final Map<String, Class<?>> formElemClasses = new HashMap<>();

    public static void register(String option, Class<?> clazz) {
        if (option != null && clazz != null) {
            formElemClasses.put(option, clazz);
        }
    }

    public static FormElement<?> getInstance(String optionNumber) {
        Class<?> formElement = formElemClasses.get(optionNumber);
        try {
            Constructor<?> cons = formElement.getConstructor();
            return (FormElement<?>) cons.newInstance();
        } catch (Exception e){
            return null;
        }
    }

    public static Map<String, Class<?>> getFormElemClasses() {
        return formElemClasses;
    }
}
