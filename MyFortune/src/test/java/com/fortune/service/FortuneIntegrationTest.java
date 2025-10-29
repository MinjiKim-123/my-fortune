package com.fortune.service;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fortune.dto.FortuneDto;
import com.fortune.dto.users.SaveUserDto;
import com.fortune.entity.code.UserGenderCode;
import com.fortune.repository.UsersRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@Transactional
class FortuneIntegrationTest {

	@Autowired
	private FortuneService fortuneService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UsersRepository usersRepository;
	
	
	int testUserIdx = 7;
	
	void init () {
		usersRepository.findById(testUserIdx).ifPresentOrElse(item -> {
			if (item.getDelYn()) 
				item.setDelYn(false);
			usersRepository.save(item);
		}, () -> {
			SaveUserDto userDto = new SaveUserDto();
			userDto.setUserId("testUser");
			userDto.setName("테스트유저");
			userDto.setPwd("a12345678!");
			userDto.setBirthDt(LocalDateTime.of(2000, 1, 1, 1, 1));
			userDto.setGender(UserGenderCode.WOMAN);
			userDto.setEmail("testUser@gmail.com");
			userDto.setPhone("01011111111");
			userDto.setSolarYn("Y");
			testUserIdx = userService.registUser(userDto);
		});
	}
	
	@Test
	void testGetTodayFortune() {
		init();
		FortuneDto fortuneDto = fortuneService.getTodayFortune(testUserIdx);
		assertNotNull(fortuneDto);
        assertFalse(fortuneDto.getFortune().isEmpty());
        assertFalse(fortuneDto.getName().isEmpty());
	}

}
