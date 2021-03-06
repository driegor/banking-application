package com.company.ebanking.common.dto.response.error;

public class FieldErrorDTO {

    private final String field;

    private final String message;

    public FieldErrorDTO(String field, String message) {
	this.field = field;
	this.message = message;
    }

    public String getField() {
	return field;
    }

    public String getMessage() {
	return message;
    }
}
