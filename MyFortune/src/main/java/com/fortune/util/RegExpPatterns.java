package com.fortune.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import java.util.regex.Pattern;

/**
 * 정규식 패턴 
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegExpPatterns {

	private static final Pattern ID_PATTERN = Pattern.compile("^[a-zA-Z\\d]{6,20}$");

	private static final Pattern PWD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$");
	
	private static final Pattern PHONE_PATTERN = Pattern.compile("^[\\d]{10,11}$");

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

	@Getter
	@RequiredArgsConstructor
	public enum Type {
		REG_ID(ID_PATTERN, "아이디는 영문 대소문자,숫자를 조합해서 6자~20자로 입력해주세요."),
		REG_PWD(PWD_PATTERN, "비밀번호는 대소문자,특수문자,숫자를 한가지 이상씩 8~20자로 입력해주세요."),
		REG_PHONE(PHONE_PATTERN, "연락처는 숫자만 10~11자로 입력해주세요."),
		REG_EMAIL(EMAIL_PATTERN, "이메일 형식이 맞지 않습니다.");
		
		private final Pattern pattern;
		private final String errorMessage;
	}
}
