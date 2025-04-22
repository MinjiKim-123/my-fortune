package com.fortune.service;

import com.fortune.dto.users.RegistUserDto;
import com.fortune.entity.Users;
import com.fortune.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(UserServiceTest.class);
	@Mock
	private UsersRepository usersRepository;
	
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@InjectMocks
	private UserService userService;

	@Test
	@DisplayName("회원 등록 성공 테스트")
	void registUser_success() {
		//given
		RegistUserDto dto = new RegistUserDto("userId1", "password12!", "nickname1");

		Users savedUser = Users.builder()
				.idx(1)
				.build();

		when(usersRepository.save(any(Users.class))).thenReturn(savedUser);

		// when
		int userIdx = userService.registUser(dto);

		// then
		assertEquals(1, userIdx);
		verify(usersRepository, times(1)).save(any(Users.class));
	}
	
	@ParameterizedTest
	@CsvSource(value = {"user1, '', ''" ,
			"한글아이디1, '', ''",
			"userId1, 'pwd', ''",
			"userId1, 'password1', ''",
			"userId1, 'password1!', '닉네임15자이상오류발생시키기.'"})
	@DisplayName("회원 등록 실패 테스트 - 입력 값 오류")
	void registUser_failed_invalidValue(String userId, String pwd, String nickNm) {
		//given
		RegistUserDto dto = new RegistUserDto(userId, pwd, nickNm);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.registUser(dto));
		log.info("오류 메세지: " + exception.getMessage());
	}
}