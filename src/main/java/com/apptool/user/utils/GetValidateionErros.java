package com.apptool.user.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;

public class GetValidateionErros {
    
    public static Map<String, String> getValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}
