package com.company.ebanking.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.company.ebanking.common.dto.response.error.ErrorMessageDTO;
import com.company.ebanking.common.dto.response.error.ValidationErrorDTO;
import com.company.ebanking.rest.controller.responses.Responses;

public class RestErrorHandlerController {

    @Value("${error.request.userMessage}")
    private String userErrorMessage;

    private static final Logger LOGGER = LoggerFactory.getLogger(RestErrorHandlerController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDTO> methodArgumentNotValidException(HttpServletRequest req, Exception ex) {
	String errorURL = req.getRequestURL().toString();
	return Responses.internalError(
		new ErrorMessageDTO(HttpStatus.INTERNAL_SERVER_ERROR, errorURL, userErrorMessage, ex.getMessage()));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ErrorMessageDTO> methodArgumentNotValidException(HttpServletRequest req,
	    MethodArgumentNotValidException ex) {
	String errorURL = req.getRequestURL().toString();
	BindingResult result = ex.getBindingResult();
	List<FieldError> fieldErrors = result.getFieldErrors();
	return Responses.badRequest(new ErrorMessageDTO(HttpStatus.BAD_REQUEST, errorURL, userErrorMessage,
		ex.getMessage(), processFieldErrors(fieldErrors)));
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
	ValidationErrorDTO dto = new ValidationErrorDTO();

	for (FieldError fieldError : fieldErrors) {
	    String localizedErrorMessage = fieldError.getDefaultMessage();
	    LOGGER.debug("Adding error message: {} to field: {}", localizedErrorMessage, fieldError.getField());
	    dto.addFieldError(fieldError.getField(), localizedErrorMessage);
	}
	return dto;
    }

}
