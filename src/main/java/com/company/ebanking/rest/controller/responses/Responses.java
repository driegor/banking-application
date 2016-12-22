package com.company.ebanking.rest.controller.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Responses {

	private Responses() {

	}

	public static <T> ResponseEntity<T> success(T model) {
		return new ResponseEntity<>(model, HttpStatus.OK);
	}

	public static <E> ResponseEntity<E> unAuthorized(E error) {
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	public static <E> ResponseEntity<E> notFound(E error) {
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	public static <E> ResponseEntity<E> badRequest(E error) {
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	public static <E> ResponseEntity<E> internalError(E error) {
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static <C> ResponseEntity<C> custom(HttpStatus customCode, C custom) {
		return new ResponseEntity<>(custom, customCode);
	}

	public static <E> ResponseEntity<E> forbidden(E error) {
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

}
