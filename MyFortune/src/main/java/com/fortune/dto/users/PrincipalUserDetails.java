package com.fortune.dto.users;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Getter
public class PrincipalUserDetails implements UserDetails {

	private String userId;
	
	private Integer userIdx;
	
	private String userPwd;

	private final Collection<? extends GrantedAuthority> authorities;

	@Override
	public String getPassword() {
		return this.userPwd;
	}

	@Override
	public String getUsername() {
		return this.userId;
	}

	public void erasePwd(){
		this.userPwd = null;
	}
}
