package com.algamoney.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlgaMoneyExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String messageUser = messageSource.getMessage("invalid.data", null, LocaleContextHolder.getLocale());
		String messageDeveloper = Optional.ofNullable(ex.getCause()).orElse(ex).toString();
		List<Error> errors = Arrays.asList(new Error(messageUser, messageDeveloper));
		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Error> errors = listErrors(ex.getBindingResult());
		
		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleEmptyResultDataAccessException() {
		
	}
	
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		String messageUser = messageSource.getMessage("resource.operation-not-permited", null, LocaleContextHolder.getLocale());
		String messageDeveloper = ExceptionUtils.getRootCauseMessage(ex);
		List<Error> errors = Arrays.asList(new Error(messageUser, messageDeveloper));
		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Error> listErrors(BindingResult bindingResult) {
		List<Error> errors = new ArrayList<>();
		
		bindingResult.getFieldErrors().stream().forEach(error -> {
			String messageUser = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			String messageDeveloper = error.toString();
			errors.add(new Error(messageUser, messageDeveloper));
		});
		
		return errors;
	}
	
	public static class Error {
		private String messageUser;
		private String messageDeveloper;
		
		public Error(String messageUser, String messageDeveloper) {
			super();
			this.messageUser = messageUser;
			this.messageDeveloper = messageDeveloper;
		}

		public String getMessageUser() {
			return messageUser;
		}

		public void setMessageUser(String messageUser) {
			this.messageUser = messageUser;
		}

		public String getMessageDeveloper() {
			return messageDeveloper;
		}

		public void setMessageDeveloper(String messageDeveloper) {
			this.messageDeveloper = messageDeveloper;
		}
	}
}
