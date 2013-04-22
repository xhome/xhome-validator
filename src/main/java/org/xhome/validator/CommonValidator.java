package org.xhome.validator;

import org.springframework.validation.Errors;

/**
 * @project validator
 * @author jhat
 * @email cpf624@126.com
 * @date Feb 11, 201310:00:57 PM
 */
public class CommonValidator implements
		org.springframework.validation.Validator {
	
	private Validator[]	validators;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		if (validators != null) {
			for (Validator validator : validators) {
				if (!validator.validate(target, errors)) {
					break;
				}
			}
		}
	}
	
	public void setValidators(Validator[] validators) {
		this.validators = validators;
	}
	
	public Validator[] getValidators() {
		return validators;
	}
	
}
