package org.xhome.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xhome.util.ClassUtils;
import org.xhome.util.StringUtils;
import org.xhome.validator.mapping.Mapping;

/**
 * @project validator
 * @author jhat
 * @email cpf624@126.com
 * @date Feb 11, 20139:52:11 PM
 */
public final class ValidatorMapping {
	
	private Map<String, String>			validatorMappings;
	private Map<String, Validator>		cachedValidators;
	private Map<String, Validator[]>	cachedValidatorMappings;
	private Log							logger;
	private static ValidatorMapping		validatorMapping	= new ValidatorMapping();
	
	private ValidatorMapping() {
		logger = LogFactory.getLog(ValidatorMapping.class);
		validatorMappings = new HashMap<String, String>();
		cachedValidators = new HashMap<String, Validator>();
		cachedValidatorMappings = new HashMap<String, Validator[]>();
		
		Set<Class<?>> mappings = ClassUtils.findClasses(Mapping.class);
		if (mappings != null && mappings.size() > 0) {
			logger.debug("load validator mappings from implements of Mapping");
			for (Class<?> clazz : mappings) {
				try {
					Mapping map = (Mapping) clazz.newInstance();
					Map<String, String> vmaps = map.validatorMappings();
					validatorMappings.putAll(vmaps);
					logger.debug("load validator mappings from "
							+ clazz.getName());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		
		try {
			Properties p = new Properties();
			p.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("validator-mappings.properties"));
			logger.debug("load validator mappings from validator-mappings.properties");
			Set<Object> vs = p.keySet();
			for (Object v : vs) {
				String key = (String) v;
				validatorMappings.put(key, (String) p.get(key));
			}
		} catch (Exception e) {}
		
	}
	
	public static ValidatorMapping getInstance() {
		return validatorMapping;
	}
	
	public Validator[] getValidatorByUri(String uri) {
		String validator = null, key = uri;
		int index = -1;
		while (true) {
			validator = validatorMappings.get(key);
			if (validator != null) {
				break;
			}
			index = key.indexOf("/", 1);
			if (index == -1) {
				break;
			}
			key = key.substring(index);
		}
		Validator[] v = cachedValidatorMappings.get(key);
		
		if (v == null) {
			try {
				v = this.getValidatorsByName(validator);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			if (v != null) {
				cachedValidatorMappings.put(key, v);
			}
		}
		
		if (logger.isDebugEnabled()) {
			if (v != null) {
				logger.debug("find validator " + validator + " for [" + uri
						+ "]");
			} else {
				logger.debug("can't find validator " + validator + " for ["
						+ uri + "]");
			}
		}
		
		return v;
	}
	
	public Validator getValidatorByName(String name) {
		Validator validator = null;
		if (StringUtils.isNotEmpty(name)) {
			name = name.trim();
			validator = cachedValidators.get(name);
			if (validator == null) {
				try {
					validator = (Validator) Class.forName(name).newInstance();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				if (validator != null) {
					cachedValidators.put(name, validator);
				}
			}
		}
		if (logger.isDebugEnabled()) {
			if (validator != null) {
				logger.debug("find validator " + name);
			} else {
				logger.debug("can't find validator " + name);
			}
		}
		
		return validator;
	}
	
	public Validator[] getValidatorsByName(String names) {
		if (StringUtils.isNotEmpty(names)) {
			int count = 0;
			List<Validator> validators = new ArrayList<Validator>();
			String[] ns = names.split(",");
			for (String n : ns) {
				Validator validator = this.getValidatorByName(n);
				if (validator != null) {
					validators.add(validator);
					count++;
				}
			}
			if (count > 0) {
				Validator[] results = new Validator[count];
				validators.toArray(results);
				return results;
			}
		}
		return null;
	}
	
}
