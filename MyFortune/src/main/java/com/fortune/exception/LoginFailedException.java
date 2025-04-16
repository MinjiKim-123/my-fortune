package com.fortune.exception;

import com.fortune.code.LoginErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class LoginFailedException extends AuthenticationException {

	private final LoginErrorCode errorCode;

	public LoginFailedException(LoginErrorCode loginErrorCode, String message) {
		super(message);
		this.errorCode = loginErrorCode;
	}

	public LoginFailedException(LoginErrorCode loginErrorCode, String message, Throwable e) {
		super(message, e);
		this.errorCode = loginErrorCode;
	}

	public LoginFailedException(String message, Throwable e) {
		super(message, e);
		this.errorCode = null;
	}

}
