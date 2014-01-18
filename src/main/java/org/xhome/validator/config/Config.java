package org.xhome.validator.config;

import java.util.Map;

/**
 * @project xhome-validator
 * @author jhat
 * @email cpf624@126.com
 * @date Feb 11, 201310:19:18 PM
 * @description 校验配置接口
 * 实现该接口，且package为org.xhome.validator.config的类将被自动加载
 */
public interface Config {
	
	/**
	 * 返回校验配置项
	 * @return
	 */
	public Map<String, String> validatorConfigs();
	
}
