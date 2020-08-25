package com.company.dynamic.form;

import com.company.dynamic.form.elements.*;
import com.company.utils.*;

import java.util.List;
import java.util.Map;

public class DynamicFormHandler {
    private final DynamicFormService service;

    static {
        FormElementFactory.register("1", Text.class);
        FormElementFactory.register("2", TextNumber.class);
        FormElementFactory.register("3", TextPassword.class);
        FormElementFactory.register("4", DateField.class);
        FormElementFactory.register("5", Checkbox.class);
        FormElementFactory.register("6", RadioButton.class);
        FormElementFactory.register("7", Dropdown.class);
    }

    public DynamicFormHandler(DynamicFormService service) {
        this.service = service;
    }

    public void start() {
        while (true) {
            System.out.print("Choose an option\n" +
                    "1) Create new form template    2) Fill form from Template\n" +
                    "3) Print filled form           Exit\nOption: ");
            String option = InputReader.readLine().toLowerCase();
            switch (option) {
                case "1":
                    DynamicForm form = createTemplateForm();
                    saveForm(form, "Template");
                    break;
                case "2":
                    DynamicForm templateForm = getForm("Template");
                    if (templateForm != null) {
                        DynamicForm filledForm = fillFormFromTemplate(templateForm);
                        filledForm.setFilled(true);
                        saveForm(filledForm, "Filled");
                    }
                    break;
                case "3":
                    printFilledForm();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private DynamicForm getForm(String formType) {
        System.out.printf("Enter file path where the %s form is saved%nPath: ", formType);
        String filePath = InputReader.readLine();
        Response<?> response = service.getForm(FileFormat.JSON, filePath);
        if (response.isSuccessful()) {
            return (DynamicForm) response.getMsg();
        } else {
            System.out.println(response.getMsg());
            return null;
        }
    }

    private void saveForm(DynamicForm form, String formType) {
        System.out.printf("Enter file path where the %s form will be saved%nPath: ", formType);
        String filePath = InputReader.readLine();
        Response<?> response = service.saveForm(FileFormat.JSON, filePath, form);
        if (response.isSuccessful()) {
            System.out.printf("%s form was stored successfully in file \"%s\"\n", formType, filePath);
        } else {
            System.out.println(response.getMsg());
        }
    }

    private void printFilledForm() {
        DynamicForm filledForm = getForm("Filled");
        if (filledForm != null) {
            System.out.printf("----- %s -----%n", filledForm.getName());
            if (filledForm.isFilled()) {
                for (FormElement<?> element : filledForm.getFormElements()) {
                    System.out.println(element);
                }
            } else {
                System.out.println("Can't print! The form is not filled");
            }
            System.out.printf("----- %s -----%n", filledForm.getName());
        }
    }

    private DynamicForm fillFormFromTemplate(DynamicForm templateForm) {
        DynamicForm filledForm = new DynamicForm();
        filledForm.setName(templateForm.getName());
        int filledField = 0;
        List<FormElement<?>> formElements = templateForm.getFormElements();
        System.out.println("--- Filling form \"" + templateForm.getName() + "\" ---");
        while (filledField < formElements.size()) {
            FormElement<?> fieldTemplate = formElements.get(filledField);
            System.out.print("Enter \"" + fieldTemplate.getName() + "\": ");
            System.out.print(fieldTemplate.showValueOptions());
            String line = InputReader.readLine();
            List<String> violations = fieldTemplate.validate(line);
            if (violations.size() == 0) {
                fieldTemplate.defineValue(line);
                filledForm.addFormElement(fieldTemplate);
                filledField++;
            } else {
                printViolations(violations);
            }
        }
        return filledForm;
    }

    private void printViolations(List<String> violations) {
        for (String violation : violations) {
            System.out.println(violation);
        }
    }

    private DynamicForm createTemplateForm() {
        DynamicForm form = new DynamicForm();
        System.out.print("Enter a name for the form: ");
        form.setName(InputReader.readLine());
        addFormFieldsToTemplate(form);
        return form;
    }

    private void addFormFieldsToTemplate(DynamicForm form) {
        while (true) {
            System.out.printf("Adding elements to form \"%s\"%n", form.getName());
            printFormElements();
            String option = InputReader.readLine().toLowerCase();
            if ("exit".equalsIgnoreCase(option)) {
                break;
            } else {
                FormElement<?> formElement = FormElementFactory.getInstance(option);
                if (formElement == null) {
                    System.out.println("Invalid option");
                } else {
                    String formElementType = formElement.getClass().getSimpleName();
                    formElement.setName(InputReader.readLineFor(formElementType + " field name"));
                    if (formElement instanceof Text && !(formElement instanceof TextNumber)) {
                        if (InputReader.wantsToAddValueFor("Pattern validation"))
                            ((Text) formElement).setPatternStr(InputReader.readPatternFor(formElement.getName()));
                    }
                    if (formElement instanceof Text) {
                        addRangeValidations((Text) formElement);
                    }
                    if (formElement instanceof OptionList) {
                        addOptionsFor((OptionList) formElement, formElementType);
                    }
                    form.addFormElement(formElement);
                }
            }
        }
    }

    private void printFormElements() {
        Map<String, Class<?>> formElemClasses = FormElementFactory.getFormElemClasses();
        System.out.println("Choose an option (Enter exit to finish the adding)");
        for (Map.Entry<String, Class<?>> entry : formElemClasses.entrySet()) {
            System.out.println(entry.getKey() + ") " + entry.getValue().getSimpleName());
        }
        System.out.println("Option: ");
    }

    private void addRangeValidations(Text textField) {
        if (InputReader.wantsToAddValueFor("Min validation"))
            textField.setMin(InputReader.readIntFor("Min value"));
        if (InputReader.wantsToAddValueFor("Max validation"))
            textField.setMax(InputReader.readIntFor("Max value"));
    }

    private void addOptionsFor(OptionList optionList, String description) {
        while (optionList.getOptions().size() == 0) {
            optionList.setOptions(InputReader.readMultipleLinesFor(description));
            if (optionList.getOptions().size() == 0) {
                System.out.println(description + " must have at least one option");
            }
        }
    }
}
