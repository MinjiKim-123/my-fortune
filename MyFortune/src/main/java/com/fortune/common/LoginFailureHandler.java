package com.fortune.common;

import com.fortune.code.LoginErrorCode;
import com.fortune.exception.LoginFailedException;
import com.fortune.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 로그인 실패 핸들러
 */
@Component
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		LoginErrorCode loginErrorCode;

		if(exception instanceof LoginFailedException loginFailedException)
			loginErrorCode = loginFailedException.getErrorCode();
		else
			loginErrorCode = LoginErrorCode.UNKNOWN_ERROR;

		log.error("Login failed. Error code: {}", loginErrorCode, exception);

		response.sendRedirect("/login/form?errorCode=" + loginErrorCode);
	}
}
