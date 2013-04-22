package org.xhome.validator;

import org.xhome.common.Base;
import org.springframework.validation.Errors;

/**
 * @project validator
 * @author jhat
 * @email cpf624@126.com
 * @date Feb 11, 20139:50:45 PM
 */
public class StatusValidator extends Validator {
	
	public final static String	FIELD_STATUS		= "status";
	
	public final static String	CODE_STATUS_EMPTY	= "status.empty";
	
	@Override
	public boolean validate(Object target, Errors errors) {
		Base base = (Base) target;
		if (base == null || base.getStatus() == null) {
			errors.rejectValue(FIELD_STATUS, CODE_STATUS_EMPTY,
					validationConfig
							.getConfig(ValidatorConfig.STATUS_NULL_MESSAGE));
		} else {
			return true;
		}
		return false;
	}
	
}
