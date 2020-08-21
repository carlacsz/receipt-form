package com.company.receipt.components;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class Client {
    public static final int NAME_MAX_LENGTH = 100;
    public static final int EMAIL_MAX_LENGTH = 50;
    public static final int ADDRESS_MAX_LENGTH = 50;

    @NotBlank(message = "Full name must not be empty")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Full name must contain only letters")
    @Size(max = NAME_MAX_LENGTH, message = "Full name must be max " + NAME_MAX_LENGTH + " characters long")
    private String fullName;

    @Valid
    private CI ci;

    @Email(message = "Email must be a valid email address")
    @Size(max = EMAIL_MAX_LENGTH, message = "Email must be max " + EMAIL_MAX_LENGTH + " characters long")
    private String email;

    @Size(max = ADDRESS_MAX_LENGTH, message = "Address must be max " + ADDRESS_MAX_LENGTH + " characters long")
    private String address;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public CI getCi() {
        return ci;
    }

    public void setCi(CI ci) {
        this.ci = ci;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "       Full Name       " + fullName + '\n' +
                "       CI              " + ci + '\n' +
                "       Email           " + email + '\n' +
                "       Address         " + address + '\n';
    }
}
