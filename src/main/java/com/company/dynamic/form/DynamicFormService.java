package com.company.dynamic.form;

import com.company.dynamic.form.elements.FormElement;
import com.company.dynamic.form.elements.TextPassword;
import com.company.utils.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class DynamicFormService {

    public Response<?> getForm(FileFormat fileFormat, String filePath) {
        ISerializer<DynamicForm> serializer = getFileSerializer(fileFormat);
        try {
            DynamicForm form = serializer.read(filePath, DynamicForm.class);
            return new Response<>(true, form);
        } catch (IOException e) {
            return new Response<>(false,
                    "Form could not be loaded. Deserialization failed!\n" + e.getMessage());
        }
    }

    public Response<?> saveForm(FileFormat fileFormat, String filePath, DynamicForm form) {
        if (form.isFilled()) {
            try {
                SecurePasswordFields(form);
            } catch (NoSuchAlgorithmException e) {
                new Response<>(false,
                        "Form could not be saved. Couldn't secure passwords values for form " + form.getName());
            }
        }
        ISerializer<DynamicForm> serializer = getFileSerializer(fileFormat);
        try {
            serializer.write(filePath, form);
            return new Response<>(true, form);
        } catch (IOException e) {
            return new Response<>(false,
                    "Form could not be saved. Serialization failed!\n" + e.getMessage());
        }
    }

    private ISerializer<DynamicForm> getFileSerializer(FileFormat format) {
        if (format == FileFormat.JSON) {
            return new JsonSerializer<>();
        }
        if (format == FileFormat.XML) {
            return new XmlSerializer<>();
        }
        throw new IllegalStateException("Unexpected value: " + format);
    }

    private void SecurePasswordFields(DynamicForm filledForm) throws NoSuchAlgorithmException {
        List<FormElement<?>> elements = filledForm.getFormElements();
        for (FormElement<?> element : elements
        ) {
            if (element instanceof TextPassword) {
                element.defineValue(SimpleMd5.getHash(element.getValue().toString()));
            }
        }
    }
}
