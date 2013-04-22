package org.xhome.validator;

import org.xhome.common.Base;
import org.springframework.validation.Errors;

/**
 * @project validator
 * @author jhat
 * @email cpf624@126.com
 * @date Feb 11, 20139:50:34 PM
 */
public class IdValidator extends Validator {

	public final static String	FIELD_ID		= "id";
	
	public final static String	CODE_ID_EMPTY	= "id.empty";
	
	@Override
	public boolean validate(Object target, Errors errors) {
		Base base = (Base) target;
		if (base == null || base.getId() == null) {
			errors.rejectValue(FIELD_ID, CODE_ID_EMPTY, validationConfig.getConfig(ValidatorConfig.ID_NULL_MESSAGE));
		} else {
			return true;
		}
		return false;
	}

}
