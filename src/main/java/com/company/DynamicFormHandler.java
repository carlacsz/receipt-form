package com.company;

import com.company.dynamic.form.DynamicForm;
import com.company.dynamic.form.elements.*;
import com.company.utils.FileSerializer;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DynamicFormHandler {
    private final Scanner scanner = new Scanner(System.in);
    private final FileSerializer serializer;

    public DynamicFormHandler(FileSerializer serializer) {
        this.serializer = serializer;
    }

    public void start() {
        while (true) {
            System.out.printf("Choose an option%n" +
                    "1) Create New Form Template%n2) Fill form from Template%n3) Exit%nOption number: ");
            String option = readLine();
            switch (option) {
                case "1":
                    createDynamicForm();
                    break;
                case "2":
                    fillFormFromTemplate();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private void fillFormFromTemplate() {
        System.out.println("Enter file path where the dynamic form is saved");
        String templateFormPath = readLine();
        try {
            DynamicForm templateForm = serializer.read(templateFormPath, DynamicForm.class);
            System.out.println("Form Template \"" + templateForm.getName() + "\" was loaded");
            fillTemplateForm(templateForm);
        } catch (IOException e) {
            System.out.println("Form could not be loaded. Deserialization failed!\n" + e.getMessage());
        }
    }

    private void fillTemplateForm(DynamicForm templateForm) {
        System.out.println("Enter file path where the filled form will be saved");
        String filledFormPath = readLine();
        DynamicForm filledForm = new DynamicForm();
        filledForm.setName(templateForm.getName());
        int filledField = 0;
        List<FormElement> formElements = templateForm.getFormElements();
        while (filledField < formElements.size()) {
            FormElement fieldTemplate = formElements.get(filledField);
            System.out.println("Enter value for: " + fieldTemplate.getName());
            System.out.print(fieldTemplate.showValueOptions());
            String line = readLine();
            if (fieldTemplate.validate(line).size() == 0) {
                fieldTemplate.setValue(line);
                filledForm.addFormElement(fieldTemplate);
                filledField++;
            } else {
                printViolations(fieldTemplate.validate(line));
            }
        }
        try {
            serializer.write(filledFormPath, filledForm);
            System.out.println("Filled form was stored successfully!");
        } catch (IOException e) {
            System.out.println("Filled form could not be saved. Serialization failed!\n" + e.getMessage());
        }
    }

    private void printViolations(List<String> violations) {
        for (String violation : violations) {
            System.out.println(violation);
        }
    }

    private void createDynamicForm() {
        System.out.println("Enter file path where the dynamic form will be saved");
        String filePath = readLine();
        DynamicForm form = new DynamicForm();
        System.out.println("Enter a name for the dynamic form");
        form.setName(readLine());
        addFormFieldsToTemplate(form);
        try {
            serializer.write(filePath, form);
            System.out.println("Form Template was stored successfully in file" + filePath);
        } catch (IOException e) {
            System.out.println("Form could not be saved. Serialization failed!\n" + e.getMessage());
        }
    }

    private void addFormFieldsToTemplate(DynamicForm form) {
        while (true) {
            System.out.printf("Choose which field to add %n" +
                    "1) Text%n2) Text Number%n3) Text Password%n" +
                    "4) Date%n5) Checkbox%n6) Radio Button%n" +
                    "7) Dropdown%n8) Button%n9) Exit%nOption number: ");
            String option = readLine();
            switch (option) {
                case "1":
                    form.addFormElement(createText());
                    break;
                case "2":
                    form.addFormElement(createTextNumber());
                    break;
                case "3":
                    form.addFormElement(createTextPassword());
                    break;
                case "4":
                    form.addFormElement(createDateField());
                    break;
                case "5":
                    form.addFormElement(createCheckbox());
                    break;
                case "6":
                    form.addFormElement(createRadioButton());
                    break;
                case "7":
                    form.addFormElement(createDropdown());
                    break;
                case "8":
                    form.addFormElement(createButton());
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private Text createText() {
        Text text = new Text();
        text.setName(readFieldName());
        return text;
    }

    private TextNumber createTextNumber() {
        TextNumber textNumber = new TextNumber();
        textNumber.setName(readFieldName());
        return textNumber;
    }

    private TextPassword createTextPassword() {
        TextPassword textPassword = new TextPassword();
        textPassword.setName(readFieldName());
        return textPassword;
    }

    private DateField createDateField() {
        DateField dateField = new DateField();
        dateField.setName(readFieldName());
        return dateField;
    }

    private Checkbox createCheckbox() {
        Checkbox checkbox = new Checkbox();
        checkbox.setName(readFieldName());
        return checkbox;
    }

    private RadioButton createRadioButton() {
        RadioButton radioButton = new RadioButton();
        radioButton.setName(readFieldName());
        return radioButton;
    }

    private Dropdown createDropdown() {
        Dropdown dropdown = new Dropdown();
        dropdown.setName(readFieldName());
        return dropdown;
    }

    private Button createButton() {
        Button button = new Button();
        button.setName(readFieldName());
        return button;
    }

    private String readFieldName() {
        System.out.println("Enter name for field");
        return readLine();
    }

    private String readLine() {
        return scanner.nextLine();
    }

}
