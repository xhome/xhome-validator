package org.xhome.validator.mapping;

import java.util.Map;

/**
 * @project validator
 * @author jhat
 * @email cpf624@126.com
 * @date Feb 11, 201310:02:57 PM
 * @description 校验器映射
 * 实现该接口，且package为org.xhome.validator.mapping的类将被自动加载
 */
public interface Mapping {
	
	/**
	 * 返回请求连接与校验器的映射表，一个请求连接映射一个校验器
	 * @return
	 */
	public Map<String, String> validatorMappings();
	
	/**
	 * 返回错误状态码映射表，将字符串的错误状态码映射为一个数字
	 * @return
	 */
	public Map<String, Short> codeMappings();
	
}
