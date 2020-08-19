package com.company.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class InputReader {
    private static final String INVALID_NUMBER_FORMAT = "Invalid number format, try again!";
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine() {
        return scanner.nextLine();
    }

    public static String readLineFor(String description) {
        System.out.print("Enter " + description + ": ");
        return readLine();
    }

    public static int readIntFor(String description) {
        while (true) {
            try {
                return Integer.parseInt(readLineFor(description));
            } catch (NumberFormatException e) {
                System.out.println(INVALID_NUMBER_FORMAT);
            }
        }
    }

    public static String readPatternFor(String description) {
        while (true) {
            System.out.print("Enter pattern for " + description + ": ");
            String pattern = readLine();
            try {
                Pattern.compile(pattern);
                return pattern;
            } catch (PatternSyntaxException e) {
                System.out.println("Please enter a valid pattern!");
            }
        }
    }

    public static List<String> readMultipleLinesFor(String description) {
        List<String> stringList = new ArrayList<>();
        System.out.println("Adding options for " + description);
        while (true) {
            System.out.printf("Enter new option for \"%s\" or exit to finish: ", description);
            String option = readLine();
            if ("exit".equalsIgnoreCase(option)) {
                break;
            }
            stringList.add(option);
            System.out.printf("\"%s\" has been added to \"%s\"%n", option, description);
        }
        return stringList;
    }

}
