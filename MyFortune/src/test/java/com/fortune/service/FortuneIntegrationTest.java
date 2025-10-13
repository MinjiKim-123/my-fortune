package com.fortune.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.fortune.dto.FortuneDto;
import com.fortune.dto.users.SaveUserDto;

import com.fortune.entity.code.UserGenderCode;
import com.fortune.repository.UsersRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class FortuneIntegrationTest {

	@Autowired
	private FortuneService fortuneService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UsersRepository usersRepository;
	
	int testUserIdx;
	
	
	
	@Test
	void testGetTodayFortune() {
		FortuneDto fortuneDto = fortuneService.getTodayFortune(testUserIdx);
		
		assertNotNull(fortuneDto);
		assertThat(fortuneDto.getFortune()).isNotBlank();
		assertThat(fortuneDto.getName()).isNotBlank();	
	}


}
