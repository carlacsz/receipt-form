package com.company.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JsonFileSerializer {
    public static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");

    public static Object write(String filePath, Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(dateFormat);
        Object saved = null;
        try {
            objectMapper.writeValue(new File(filePath), obj);
            saved = obj;
        } catch (IOException e) {
//            System.out.println(e.toString());
        }
        return saved;
    }

    public static Object read(String filePath, Class clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(dateFormat);
        Object obj = null;
        try {
            obj = objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
//            System.out.println(e.toString());
        }
        return obj;
    }
}
