package com.probot.exceptions;

import java.util.ArrayList;
import java.util.List;

public class InvalidInputException extends Exception {

	private static final long serialVersionUID = 1L;
	private List<String> errors = new ArrayList<String>();

	public InvalidInputException() {
		super();
	}

	public InvalidInputException(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
