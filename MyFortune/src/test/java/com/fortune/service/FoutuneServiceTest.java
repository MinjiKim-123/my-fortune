package com.fortune.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortune.dto.FortuneDto;
import com.fortune.dto.users.UserDto;
import com.fortune.entity.code.UserGenderCode;
import com.fortune.repository.UsersRepository;
import com.fortune.util.ChatUtil;
import com.fortune.util.StringRedisUtil;

@ExtendWith(MockitoExtension.class)
class FoutuneServiceTest {
	
	@Mock
	private UsersRepository usersRepository;
	
	@Mock
	private UserService userService;
	
	@Mock
	private StringRedisUtil stringRedisUtil;
	
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@InjectMocks
	private FortuneService fortuneService;
	
	@Mock
	private ObjectMapper objectMapper;

	
	@Test
	@DisplayName("오늘의 운세 조회 성공 테스트")
	void testGetTodayFortune_SUCCESS() throws JsonMappingException, JsonProcessingException {
		//given
		int userIdx = 1;
		String userName = "사용자1";
		String todayFortune = "오늘의 운세입니다.";
		
		UserDto user1 = UserDto.builder()
				.idx(userIdx)
				.name(userName)
				.birthDt(LocalDateTime.of(1999, 10, 10, 1, 2, 3))
				.gender(UserGenderCode.WOMAN)
				.build();	

		when(userService.getUser(user1.getIdx())).thenReturn(user1);		
		when(stringRedisUtil.getData(any())).thenReturn(null);
		
		try(MockedStatic<ChatUtil> chatUtil = mockStatic(ChatUtil.class)) {
			ChatResponse chatResponse = mock(ChatResponse.class);
			chatUtil.when(() ->
                ChatUtil.call(any(), any(), any())
					).thenReturn(chatResponse);
	
			String resJsonText = "{\"name\":\"" + userName + "\",\"fortune\":\"" + todayFortune + "\"}";
			chatUtil.when(() ->
                ChatUtil.getResponseText(any())
					).thenReturn(resJsonText);
		
	        when(objectMapper.readValue(resJsonText, FortuneDto.class)).thenReturn(new FortuneDto(userName, todayFortune));
	        
			//when
	        FortuneDto fortuneDto = fortuneService.getTodayFortune(userIdx);
	        
	        // then
	        assertNotNull(fortuneDto);
			assertEquals(fortuneDto.getFortune(), todayFortune);
			assertEquals(fortuneDto.getName(), userName);
	    }			
	}
	
}
