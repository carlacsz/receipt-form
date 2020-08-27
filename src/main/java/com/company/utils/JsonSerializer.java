package com.company.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

public class JsonSerializer<T> implements ISerializer<T> {
    public static final Logger LOGGER = Logger.getLogger(JsonSerializer.class.getName());
    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm a";

    @Override
    public T write(String filePath, T obj) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), obj);
            return obj;
        } catch (IOException e) {
            LOGGER.warning(String.format("Could not serialize object with type \"%s\" as json to file \"%s\"",
                    obj.getClass(), filePath));
            LOGGER.warning(e.toString());
            throw e;
        }
    }

    @Override
    public T read(String filePath, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        try {
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            LOGGER.warning(String.format("Could not deserialize object from file \"%s\" and given class \"%s\"",
                    filePath, clazz));
            LOGGER.warning(e.toString());
            throw e;
        }
    }
}
