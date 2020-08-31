package com.company.dynamicform.elements;

import com.company.utils.Encryptor;
import com.fasterxml.jackson.annotation.JsonSetter;

public class TextPassword extends Text {


    @Override
    public void defineValue(String input) throws Exception {
        try {
            value = Encryptor.encrypt(input);
        } catch (Exception e) {
            throw new Exception("Could not encrypt value: " + input);
        }
    }

    @JsonSetter
    private void setValue(String savedValue) {
        try {
            value = Encryptor.decrypt(savedValue);
        } catch (Exception e) {
            value = "Could not decrypt value";
        }
    }
}
