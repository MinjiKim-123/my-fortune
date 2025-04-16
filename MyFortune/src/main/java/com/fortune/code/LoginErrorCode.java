package com.fortune.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginErrorCode {

	ID_OR_PWD_ERROR("아이디 또는 비밀번호를 확인해주세요."),
	IS_LOCKED_ERROR("잠김 계정입니다. 비밀번호 초기화 후 로그인 해주세요."),
	UNKNOWN_ERROR("로그인 실패. 관리자에게 문의주세요.");

	private final String errorMessage;
}
