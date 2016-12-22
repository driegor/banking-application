package com.company.ebanking.common.dto.response.error;

import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ErrorMessageDTO {

    String httpStatus;
    int httpStatusCode;

    UUID apiCode;
    String userMessage;
    ValidationErrorDTO validationError;
    String detailMessage;
    String errorUrl;

    public ErrorMessageDTO(HttpStatus httpStatus, String errorURL, String userMessage, String detailMessage) {
	this(httpStatus, errorURL, userMessage, detailMessage, null);
    }

    public ErrorMessageDTO(HttpStatus httpStatus, String errorURL, String detailMessage) {
	this(httpStatus, errorURL, null, detailMessage, null);

    }

    public ErrorMessageDTO(HttpStatus httpStatus, String errorURL, String userMessage, String detailMessage,
	    ValidationErrorDTO validationError) {
	this.httpStatusCode = httpStatus.value();
	this.httpStatus = httpStatus.getReasonPhrase();
	this.errorUrl = errorURL;
	this.apiCode = UUID.randomUUID();
	this.userMessage = userMessage;
	this.detailMessage = detailMessage;
	this.validationError = validationError;
    }

    public String getHttpStatus() {
	return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
	this.httpStatus = httpStatus;
    }

    public int getHttpStatusCode() {
	return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
	this.httpStatusCode = httpStatusCode;
    }

    public UUID getApiCode() {
	return apiCode;
    }

    public void setApiCode(UUID apiCode) {
	this.apiCode = apiCode;
    }

    public String getUserMessage() {
	return userMessage;
    }

    public void setUserMessage(String userMessage) {
	this.userMessage = userMessage;
    }

    public ValidationErrorDTO getValidationError() {
	return validationError;
    }

    public void setValidationError(ValidationErrorDTO validationError) {
	this.validationError = validationError;
    }

    public String getDetailMessage() {
	return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
	this.detailMessage = detailMessage;
    }

    public String getErrorUrl() {
	return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
	this.errorUrl = errorUrl;
    }

}
