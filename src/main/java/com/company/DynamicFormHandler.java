package com.company;

import com.company.dynamic.form.DynamicForm;
import com.company.dynamic.form.elements.*;
import com.company.utils.FileSerializer;
import com.company.utils.InputReader;

import java.io.IOException;
import java.util.List;

public class DynamicFormHandler {

    private final FileSerializer serializer;

    public DynamicFormHandler(FileSerializer serializer) {
        this.serializer = serializer;
    }

    public void start() {
        while (true) {
            System.out.print("Choose an option\n" +
                    "1) Create new form template    2) Fill form from Template\n" +
                    "3) Print filled form           4) Exit\nOption number: ");
            String option = InputReader.readLine();
            switch (option) {
                case "1":
                    DynamicForm form = createTemplateForm();
                    saveForm(form, "Template");
                    break;
                case "2":
                    DynamicForm templateForm = getForm("Template");
                    if (templateForm != null) {
                        fillFormFromTemplate(templateForm);
                    }
                    break;
                case "3":
                    printFilledForm();
                    break;
                case "4":
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
        try {
            DynamicForm form = serializer.read(filePath, DynamicForm.class);
            System.out.printf("%s form \"%s\" was successfully loaded from file \"%s\"\n",
                    formType, form.getName(), filePath);
            return form;
        } catch (IOException e) {
            System.out.printf("%s form could not be loaded. Deserialization failed!\n%s",
                    formType, e.getMessage());
            return null;
        }
    }

    private void saveForm(DynamicForm form, String formType) {
        System.out.printf("Enter file path where the %s form will be saved%nPath: ", formType);
        String filePath = InputReader.readLine();
        try {
            serializer.write(filePath, form);
            System.out.printf("%s form was stored successfully in file \"%s\"\n", formType, filePath);
        } catch (IOException e) {
            System.out.printf("%s form could not be saved. Serialization failed!\n%s", formType, e.getMessage());
        }
    }

    private void printFilledForm() {
        DynamicForm filledForm = getForm("Filled");
        if (filledForm != null) {
            System.out.printf("----- %s -----%n", filledForm.getName());
            for (FormElement element : filledForm.getFormElements()) {
                System.out.println(element);
            }
            System.out.printf("----- %s -----%n", filledForm.getName());
        }
    }

    private void fillFormFromTemplate(DynamicForm templateForm) {
        DynamicForm filledForm = new DynamicForm();
        filledForm.setName(templateForm.getName());
        int filledField = 0;
        List<FormElement> formElements = templateForm.getFormElements();
        System.out.println("--- Filling form \"" + templateForm.getName() + "\" ---");
        while (filledField < formElements.size()) {
            FormElement fieldTemplate = formElements.get(filledField);
            System.out.print("Enter \"" + fieldTemplate.getName() + "\": ");
            System.out.print(fieldTemplate.showValueOptions());
            String line = InputReader.readLine();
            if (fieldTemplate.validate(line).size() == 0) {
                fieldTemplate.setValue(line);
                filledForm.addFormElement(fieldTemplate);
                filledField++;
            } else {
                printViolations(fieldTemplate.validate(line));
            }
        }
        saveForm(filledForm, "Filled");
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
            System.out.printf("Choose an option (exit option will finish the adding)%n" +
                    "1) Text      2) Text Number    3) Text Password%n" +
                    "4) Date      5) Checkbox       6) Radio Button%n" +
                    "7) Dropdown  8) Button         9) Exit%nOption number: ");
            String option = InputReader.readLine();
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
                    form.addFormElement(addSaveButton());
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
        text.setName(InputReader.readLineFor("Text field name"));
        if (wantsToAddValidation(text.getName())) {
            text.setPatternStr(InputReader.readPatternFor(text.getName()));
            text.setMin(InputReader.readIntFor("Min value"));
            text.setMax(InputReader.readIntFor("Max value"));
        }
        return text;
    }

    private TextNumber createTextNumber() {
        TextNumber textNumber = new TextNumber();
        textNumber.setName(InputReader.readLineFor("Text Number field name"));
        if (wantsToAddValidation(textNumber.getName())) {
            textNumber.setMin(InputReader.readIntFor("Min value"));
            textNumber.setMax(InputReader.readIntFor("Max value"));
        }
        return textNumber;
    }

    private TextPassword createTextPassword() {
        TextPassword textPassword = new TextPassword();
        textPassword.setName(InputReader.readLineFor("Text password field name"));
        if (wantsToAddValidation(textPassword.getName())) {
            textPassword.setPatternStr(InputReader.readPatternFor(textPassword.getName()));
            textPassword.setMin(InputReader.readIntFor("Min value"));
            textPassword.setMax(InputReader.readIntFor("Max value"));
        }
        return textPassword;
    }

    private DateField createDateField() {
        DateField dateField = new DateField();
        dateField.setName(InputReader.readLineFor("Date field name"));
        return dateField;
    }

    private Checkbox createCheckbox() {
        Checkbox checkbox = new Checkbox();
        checkbox.setName(InputReader.readLineFor("Checkbox field name"));
        return checkbox;
    }

    private RadioButton createRadioButton() {
        RadioButton radioButton = new RadioButton();
        radioButton.setName(InputReader.readLineFor("Radio button field name"));
        while (radioButton.getOptions().size() == 0) {
            radioButton.setOptions(InputReader.readMultipleLinesFor("Radio Button"));
            if (radioButton.getOptions().size() == 0) {
                System.out.println("Radio Button must have at least one option");
            }
        }
        return radioButton;
    }

    private Dropdown createDropdown() {
        Dropdown dropdown = new Dropdown();
        dropdown.setName(InputReader.readLineFor("Dropdown field name"));
        while (dropdown.getOptions().size() == 0) {
            dropdown.setOptions(InputReader.readMultipleLinesFor("Dropdown"));
            if (dropdown.getOptions().size() == 0) {
                System.out.println("Dropdown must have at least one option");
            }
        }
        return dropdown;
    }

    private SaveButton addSaveButton() {
        SaveButton saveButton = new SaveButton();
        saveButton.setName(InputReader.readLineFor("Save button name"));
        return saveButton;
    }

    private boolean wantsToAddValidation(String fieldName) {
        System.out.printf("Enter yes if you want to add validation for field \"%s\"\n", fieldName);
        return "yes".equalsIgnoreCase(InputReader.readLine());
    }

}
