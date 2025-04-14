package com.fortune.service;

import com.fortune.dto.PricipalUserDetails;
import com.fortune.entity.Users;
import com.fortune.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class UserService implements UserDetailsService {

	private final UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Users user = usersRepository.findByUserId(userId)
				.orElseThrow(() -> new UsernameNotFoundException("Failed to find user by user id. User id : " + userId));
		
		return PricipalUserDetails.builder()
				.idx(user.getIdx())
				.username(userId)
				.password(user.getPwd())
				.nickname(user.getNickname())
				.build();
	}
}
