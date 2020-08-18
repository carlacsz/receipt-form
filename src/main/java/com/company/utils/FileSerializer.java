package com.company.utils;

import java.io.IOException;

public interface FileSerializer {
    <T> T write(String filePath, T obj) throws IOException;
    <T> T read(String filePath, Class clazz) throws IOException;
}
