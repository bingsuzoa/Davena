package com.kkomiding.davena.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class AbstractValidator<T> implements Validator {

	
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void validate(Object target, Errors errors) {
		doValidate((T) target, errors);
	}
	
	protected abstract void doValidate(final T dto, final Errors errors);
}
