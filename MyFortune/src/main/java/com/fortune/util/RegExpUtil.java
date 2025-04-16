package com.fortune.util;

import org.springframework.validation.annotation.Validated;

import java.util.regex.Pattern;

/**
 * 정규식 유틸리티
 */
public class RegExpUtil {

	private static final Pattern ID_PATTERN = Pattern.compile("^[a-zA-Z\\d]{6,20}$");

	private static final Pattern PWD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$");


	public static boolean isValidId(String id) {
		return id != null && ID_PATTERN.matcher(id).matches();
	}

	public static boolean isValidPwd(String pwd) {
		return pwd != null && PWD_PATTERN.matcher(pwd).matches();
	}
}
