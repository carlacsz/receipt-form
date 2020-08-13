package com.company.components;

import javax.validation.constraints.Min;

public class CI {
    public static final int CI_MIN_NUMBER = 1_000_000;

    @Min(value = CI_MIN_NUMBER, message = "CI number must be greater than " + CI_MIN_NUMBER)
    private int number;

    private CIExtension extension;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public CIExtension getExtension() {
        return extension;
    }

    public void setExtension(CIExtension extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return number + " " + extension;
    }
}
