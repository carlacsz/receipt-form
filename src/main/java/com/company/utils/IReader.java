package com.company.utils;

import java.util.List;

public interface IReader {
    String readLine();

    String readLineFor(String description);

    int readIntFor(String description);

    double readDoubleFor(String description);

    Enum<?> readEnumFor(String description, Class<?> enumClass);

    String readPatternFor(String description);

    List<String> readMultipleLinesFor(String description);

    boolean wantsToAddValueFor(String description);
}
