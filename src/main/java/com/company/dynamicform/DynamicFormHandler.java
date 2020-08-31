package com.company.dynamicform;

import com.company.dynamicform.elements.*;
import com.company.utils.*;

import java.util.List;
import java.util.Map;

public class DynamicFormHandler {
    private final DynamicFormService service;
    private final IReader reader;

    static {
        FormElementFactory.register("1", Text.class);
        FormElementFactory.register("2", TextNumber.class);
        FormElementFactory.register("3", TextPassword.class);
        FormElementFactory.register("4", DateField.class);
        FormElementFactory.register("5", Checkbox.class);
        FormElementFactory.register("6", RadioButton.class);
        FormElementFactory.register("7", Dropdown.class);
    }

    public DynamicFormHandler(DynamicFormService service, IReader reader) {
        this.service = service;
        this.reader = reader;
    }

    public void start() {
        while (true) {
            System.out.print("Choose an option\n" +
                    "1) Create new form template    2) Fill form from Template\n" +
                    "3) Print filled form           Exit\nOption: ");
            String option = reader.readLine().toLowerCase();
            switch (option) {
                case "1":
                    DynamicForm form = createTemplateForm();
                    saveForm(form, "Template");
                    break;
                case "2":
                    DynamicForm templateForm = getForm("Template");
                    if (templateForm != null) {
                        DynamicForm filledForm = fillFormFromTemplate(templateForm);
                        if (filledForm != null) {
                            filledForm.setFilled(true);
                            saveForm(filledForm, "Filled");
                        }
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
        FileFormat fileFormat = (FileFormat) reader
                .readEnumFor("in what file format the form is saved", FileFormat.class);
        System.out.printf("Enter file path where the %s form is saved%nPath: ", formType);
        String filePath = reader.readLine();
        Response<?> response = service.getForm(fileFormat, filePath);
        if (response.isSuccessful()) {
            return (DynamicForm) response.getMsg();
        } else {
            System.out.println(response.getMsg());
            return null;
        }
    }

    private void saveForm(DynamicForm form, String formType) {
        FileFormat fileFormat = (FileFormat) reader
                .readEnumFor("in what file format the form will be saved", FileFormat.class);
        System.out.printf("Enter file path where the %s form will be saved%nPath: ", formType);
        String filePath = reader.readLine();
        Response<?> response = service.saveForm(fileFormat, filePath, form);
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
        int filledFields = 0;
        List<FormElement<?>> formElements = templateForm.getFormElements();
        System.out.println("--- Filling form \"" + templateForm.getName() + "\" ---");
        while (filledFields < formElements.size()) {
            FormElement<?> fieldTemplate = formElements.get(filledFields);
            System.out.print("Enter \"" + fieldTemplate.getName() + "\": ");
            System.out.print(fieldTemplate.showValueOptions());
            String line = reader.readLine();
            List<String> violations = fieldTemplate.validate(line);
            if (violations.size() == 0) {
                try {
                    fieldTemplate.defineValue(line);
                    filledFields++;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return null;
                }
            } else {
                printViolations(violations);
            }
        }
        return templateForm;
    }

    private void printViolations(List<String> violations) {
        for (String violation : violations) {
            System.out.println(violation);
        }
    }

    private DynamicForm createTemplateForm() {
        DynamicForm form = new DynamicForm();
        System.out.print("Enter a name for the form: ");
        form.setName(reader.readLine());
        addFormFieldsToTemplate(form);
        return form;
    }

    private void addFormFieldsToTemplate(DynamicForm form) {
        while (true) {
            System.out.printf("Adding elements to form \"%s\"%n", form.getName());
            printFormElements();
            String option = reader.readLine().toLowerCase();
            if ("exit".equalsIgnoreCase(option)) {
                break;
            } else {
                FormElement<?> formElement = FormElementFactory.getInstance(option);
                if (formElement == null) {
                    System.out.println("Invalid option");
                } else {
                    String formElementType = formElement.getClass().getSimpleName();
                    formElement.setName(reader.readLineFor(formElementType + " field name"));
                    formElement.fillValidations(reader);
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
}
