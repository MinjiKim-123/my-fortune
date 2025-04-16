package com.fortune.config;

import com.fortune.common.CustomAuthenticationProvider;
import com.fortune.common.LoginFailureHandler;
import com.fortune.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

	private final LoginFailureHandler loginFailureHandler;

	/**
	 * 정적 자원 ignore 처리
	 * @return
	 */
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() { // 비밀번호 암호화 객체
		return new BCryptPasswordEncoder(); // BCrypt 알고리즘을 사용하여 비밀번호를 암호화.
		// BCrypt는 단방향 해시 함수.
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic(AbstractHttpConfigurer::disable) //
				.formLogin(formLogin -> formLogin
						.loginPage("/login/form")
						.loginProcessingUrl("/login")
						.failureHandler(loginFailureHandler)
						.defaultSuccessUrl("/"))
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
						.requestMatchers("/join/**", "/", "/login/**").permitAll()  //로그인,가입,메인 페이지는 인증 없이 사용자가 접근할 수 있도록 허용
						.anyRequest().authenticated())
				.logout((logout) -> logout.logoutSuccessUrl("/"));

		return http.build();
	}
}
