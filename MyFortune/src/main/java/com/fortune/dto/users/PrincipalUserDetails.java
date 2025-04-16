package com.fortune.dto.users;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Getter
public class PrincipalUserDetails implements UserDetails {

	private final UserDto user;

	private final Collection<? extends GrantedAuthority> authorities;

	@Override
	public String getPassword() {
		return user.getPwd();
	}

	@Override
	public String getUsername() {
		return user.getUserId();
	}
}
