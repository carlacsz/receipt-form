package com.company.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

public class JsonFileSerializer {
    public static final Logger LOGGER = Logger.getLogger(JsonFileSerializer.class.getName());
    public static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");

    public static <T> T write(String filePath, T obj) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(dateFormat);
        try {
            objectMapper.writeValue(new File(filePath), obj);
            return obj;
        } catch (IOException e) {
            LOGGER.warning(String.format("Could not serialize object with type \"%s\" as json to file \"%s\"",
                    obj.getClass(), filePath));
            LOGGER.warning(e.toString());
            throw e;
        }
    }

    public static <T> T read(String filePath, Class clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(dateFormat);
        try {
            return  (T) objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            LOGGER.warning(String.format("Could not deserialize object from file \"%s\" and given class \"%s\"",
                    filePath, clazz));
            LOGGER.warning(e.toString());
            throw e;
        }
    }
}
