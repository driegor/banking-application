package com.company.ebanking.common.dto.response.error;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ValidationErrorDTO {

    private final List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public void addFieldError(String path, String message) {
	FieldErrorDTO error = new FieldErrorDTO(path, message);
	fieldErrors.add(error);
    }

    public List<FieldErrorDTO> getFieldErrors() {
	return fieldErrors;
    }
}