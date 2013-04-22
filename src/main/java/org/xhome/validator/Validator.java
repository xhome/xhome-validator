package org.xhome.validator;

import org.springframework.validation.Errors;

/**
 * @project validator
 * @author jhat
 * @email cpf624@126.com
 * @date Feb 11, 20139:51:54 PM
 */
public abstract class Validator {
	
	protected ValidatorConfig	validationConfig	= ValidatorConfig
															.getInstance();
	
	public abstract boolean validate(Object target, Errors errors);
	
}
