package com.fortune.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fortune.dto.FortuneDto;
import com.fortune.dto.users.UserDto;
import com.fortune.entity.code.UserGenderCode;
import com.fortune.repository.UsersRepository;

class FoutuneServiceTest {
	
	@Mock
	private UsersRepository usersRepository;
	
	@Mock
	private UserService userService;
	
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@InjectMocks
	private FortuneService fortuneService;

	@Test
	@DisplayName("오늘의 운세 조회 성공 테스트")
	void testGetTodayFortune_SUCCESS() {
		//given
		UserDto user1 = UserDto.builder()
				.idx(1)
				.name("사용자1")
				.birthDt(LocalDateTime.of(1999, 10, 10, 1, 2, 3))
				.gender(UserGenderCode.WOMAN)
				.build();	

		when(userService.getUser(user1.getIdx())).thenReturn(user1);
						
		// when
		FortuneDto fortuneDto = fortuneService.getTodayFortune(1);
		
		// then
		assertNotNull(fortuneDto);
		assertNotNull(fortuneDto.getFortune());
		assertNotNull(fortuneDto.getName());
		verify(userService, times(1)).getUser(1);
		verify(usersRepository, times(1)).findByIdxAndDelYn(1, false);
	}

}
