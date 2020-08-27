package com.company.utils;

import java.io.IOException;

public interface ISerializer<T> {
    T write(String filePath, T obj) throws IOException;
    T read(String filePath, Class<T> clazz) throws IOException;
}
