package org.xhome.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xhome.util.ClassUtils;
import org.xhome.validator.config.Config;

/**
 * @project validator
 * @author jhat
 * @email cpf624@126.com
 * @date Feb 11, 20139:50:56 PM
 */
public final class ValidatorConfig {
	
	public final static String		ID_NULL_MESSAGE			= "id_null_message";
	public final static String		VERSION_NULL_MESSAGE	= "version_null_message";
	public final static String		STATUS_NULL_MESSAGE		= "status_null_message";
	
	private Log						logger;
	private Map<String, String>		validatorConfigs;
	private static ValidatorConfig	validationConfig;
	
	private ValidatorConfig() {
		logger = LogFactory.getLog(ValidatorMapping.class);
		validatorConfigs = new HashMap<String, String>();
		
		validatorConfigs.put(ID_NULL_MESSAGE, "ID不能为空");
		validatorConfigs.put(VERSION_NULL_MESSAGE, "版本号不能为空");
		validatorConfigs.put(STATUS_NULL_MESSAGE, "状态标识不能为空");
		
		Set<Class<?>> configs = ClassUtils.findClasses(Config.class);
		if (configs != null && configs.size() > 0) {
			logger.debug("load validator configs from implements of Config");
			for (Class<?> clazz : configs) {
				try {
					Config config = (Config) clazz.newInstance();
					Map<String, String> vconfigs = config.validatorConfigs();
					if (vconfigs != null && !vconfigs.isEmpty()) {
						validatorConfigs.putAll(vconfigs);
						logger.debug("load validator configs from "
								+ clazz.getName());	
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		
		try {
			Properties p = new Properties();
			p.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("validator-configs.properties"));
			logger.debug("load validator configs from validator-configs.properties");
			Set<Object> vs = p.keySet();
			for (Object v : vs) {
				String key = (String) v;
				validatorConfigs.put(key, (String) p.get(key));
			}
		} catch (Exception e) {}
	}
	
	public static ValidatorConfig getInstance() {
		if (validationConfig == null) {
			synchronized (ValidatorConfig.class) {
				if (validationConfig == null) {
					validationConfig = new ValidatorConfig();
				}
			}
		}
		return validationConfig;
	}
	
	public String getConfig(String key) {
		return validatorConfigs.get(key);
	}
	
}
