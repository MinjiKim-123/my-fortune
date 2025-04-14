package com.fortune.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@Getter
public class PricipalUserDetails implements UserDetails {

	private final Integer idx;

	private final String username;

	private final String password;

	private final String nickname;

	private final Collection<? extends GrantedAuthority> authorities;

}
