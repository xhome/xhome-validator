package org.xhome.validator;

import org.xhome.common.Base;
import org.springframework.validation.Errors;

/**
 * @project validator
 * @author jhat
 * @email cpf624@126.com
 * @date Feb 11, 20139:52:36 PM
 */
public class VersionValidator extends Validator {
	
	public final static String	FIELD_VERSION		= "version";
	
	public final static String	CODE_VERSION_EMPTY	= "version.empty";
	
	@Override
	public boolean validate(Object target, Errors errors) {
		Base base = (Base) target;
		if (base == null || base.getVersion() == null) {
			errors.rejectValue(FIELD_VERSION, CODE_VERSION_EMPTY,
					validationConfig
							.getConfig(ValidatorConfig.VERSION_NULL_MESSAGE));
		} else {
			return true;
		}
		return false;
	}
	
}
