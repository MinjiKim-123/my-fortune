package com.fortune.common;

import com.fortune.code.LoginErrorCode;
import com.fortune.dto.users.PrincipalUserDetails;
import com.fortune.dto.users.UserDto;
import com.fortune.exception.LoginFailedException;
import com.fortune.service.UserService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 커스텀 인증 제공자
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final UserService userService;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * 인증 처리
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userId = authentication.getName();
		String userPwd = authentication.getPrincipal().toString();

		if(StringUtils.isBlank(userId) || StringUtils.isBlank(userPwd))
			throw new LoginFailedException(LoginErrorCode.ID_OR_PWD_ERROR, "User id or password is empty.");

		try{
			PrincipalUserDetails userDetails = userService.loadUserByUsername(userId);
			UserDto userDto = userDetails.getUser();

			if (bCryptPasswordEncoder.matches(userPwd, userDetails.getPassword())) { //비밀번호가 틀릴 경우
				userService.handleWrongPwdAttempt(userDto.getIdx());
				throw new LoginFailedException(LoginErrorCode.ID_OR_PWD_ERROR, "Bad credentials. User idx : " + userDto.getIdx());
			}
			
			userDto.erasePwd();//dto에서 비밀번호 값 제거
			userService.handleSuccessLogin(userDto.getIdx()); //로그인 성공 후 처리
			
			return new UsernamePasswordAuthenticationToken(userDto, null, userDetails.getAuthorities());
		} catch (UsernameNotFoundException e){
			log.error("Failed to find user by user id.User id: {}, errorMessage: {}", userId, e.getMessage());
			throw new LoginFailedException(LoginErrorCode.ID_OR_PWD_ERROR, "Bad credentials.", e);
		}
	}

	/**
	 * 특정 authentication 객체 지원 여부
	 * @param authentication
	 * @return
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
