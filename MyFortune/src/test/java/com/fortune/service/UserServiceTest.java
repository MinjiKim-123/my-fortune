package com.fortune.service;

import com.fortune.dto.users.SaveUserDto;
import com.fortune.entity.Users;
import com.fortune.entity.code.UserGenderCode;
import com.fortune.repository.UsersRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
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
		SaveUserDto dto = new SaveUserDto();
		
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
	@CsvSource(value = {"user1, 'password1!', '01012345678', 'email111@gmail.com'" ,
			"한글아이디1, 'password1!', '01012345678', 'email111@gmail.com'",
			"userId1, 'pwd', '01012345678', 'email111@gmail.com'",
			"userId1, 'password1', '연락처에 숫자아닌 값 넣기', 'email111@gmail.com'",
			"userId1, 'password1!', '01012345678', 'email111'",
			"userId1, 'password1!', '01012345678', 'email111@ddd'"})
	@DisplayName("회원 등록 정규식 실패 테스트")
	void registUser_failed_regExpTest(String userId, String pwd, String phone, String email) {
		SaveUserDto dto = new SaveUserDto();
		dto.setUserId(userId);
		dto.setPwd(pwd);
		dto.setPhone(phone);
		dto.setEmail(email);
		dto.setName("이름");
		dto.setBirthDt(LocalDateTime.now());
		dto.setGender(UserGenderCode.WOMAN);
		
		try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<SaveUserDto>> violations = validator.validate(dto);
			for (ConstraintViolation<SaveUserDto> violation : violations) {
				log.warn("정규식 검사 실패 - 필드: {}, 메시지: {}, 입력값: {}",
						violation.getPropertyPath(),
						violation.getMessage(),
						violation.getInvalidValue());
			}
			
			assertFalse(violations.isEmpty()); //violations이 비어있으면 검증 성공이기 때문에 empty가 false이여야 함 
		}
	}
	
	@Test
	@DisplayName("비밀번호 5회 이상 실패시 계정 잠금 테스트")
	void testLockAfterFiveWrongPwdAttempts(){
		//given
		Users user = Users.builder()
				.idx(1)
				.pwd(bCryptPasswordEncoder.encode("testpwd1~"))
				.build();
		
		when(usersRepository.findByIdxAndDelYn(user.getIdx(), false)).thenReturn(Optional.of(user));

		// when
		for(int i = 0; i<5; i++) 
			userService.handleWrongPwdAttempt(user.getIdx());
		
		//then
		assertTrue(user.getLockYn());
		assertEquals(5, user.getLoginFailCnt());
	}
	
	@Test
	@DisplayName("로그인 성공 후 처리 로직 테스트")
	void testAfterLoginSuccessHandle(){
		//given
		Users user = Users.builder()
				.idx(1)
				.loginFailCnt(5)
				.lockYn(true)
				.build();

		when(usersRepository.findByIdxAndDelYn(user.getIdx(), false)).thenReturn(Optional.of(user));
		
		//when
		userService.handleSuccessLogin(user.getIdx());

		//then
		assertFalse(user.getLockYn());
		assertEquals(0, user.getLoginFailCnt());
	}
}