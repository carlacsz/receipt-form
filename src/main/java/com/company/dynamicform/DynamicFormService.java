package com.company.dynamicform;

import com.company.utils.*;

import java.io.IOException;

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
}
