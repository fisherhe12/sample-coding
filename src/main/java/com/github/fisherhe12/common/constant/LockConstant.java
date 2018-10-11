package com.github.fisherhe12.common.constant;

/**
 * 定义锁相关的常量类
 * @author fisher
 * @date 2018-10-10
 */
public class LockConstant {
	public static final String OK = "OK";

	/** NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist. **/
	public static final String NOT_EXIST = "NX";
	public static final String EXIST = "XX";

	/** expire EX|PX, expire time units: EX = seconds; PX = milliseconds **/
	public static final String SECONDS = "EX";
	public static final String MILLISECONDS = "PX";
}
