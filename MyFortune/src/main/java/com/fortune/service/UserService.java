package com.fortune.service;

import com.fortune.dto.users.PrincipalUserDetails;
import com.fortune.dto.users.SaveUserDto;
import com.fortune.dto.users.UserDto;
import com.fortune.entity.Users;
import com.fortune.repository.UsersRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Validated
public class UserService implements UserDetailsService {

	private final UsersRepository usersRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public PrincipalUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Users user = usersRepository.findByUserIdAndDelYn(userId, false)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found."));

		return PrincipalUserDetails.builder()
				.userId(user.getUserId())
				.userIdx(user.getIdx())
				.userPwd(user.getPwd())
				.build();
	}

	/**
	 * 아이디 존재 여부 조회
	 * @param newId 검색할 아이디
	 * @return 존재 여부
	 */
	public boolean idExists(String newId) {
		return usersRepository.existsByUserIdAndDelYn(newId, false);
	}

	/**
	 * 사용자 등록
	 * @param userDto 등록할 데이터
	 */
	public int registUser(@Valid SaveUserDto userDto) {
		String encodedPwd = bCryptPasswordEncoder.encode(userDto.getPwd());
		String encodedEmail = userDto.getEmail();
		String encodedPhone = userDto.getPhone(); //TODO 암호화 추가 예정
		
		Users newUser = usersRepository.save(Users.builder()
				.userId(userDto.getUserId())
				.pwd(encodedPwd)
				.name(userDto.getName())
				.email(encodedEmail)
				.phone(encodedPhone)
				.birthDt(userDto.getBirthDt())
				.gender(userDto.getGender())
				.build());

		return newUser.getIdx();
	}

	/**
	 * 비밀번호 오류 처리
	 * @param userIdx 회원 idx
	 */
	@Transactional
	public void handleWrongPwdAttempt(@NotNull @Min(1) Integer userIdx) {
		Users user = usersRepository.findByIdxAndDelYn(userIdx, false)
				.orElseThrow(() -> new UsernameNotFoundException("Failed to find user by user idx."));

		user.incLoginFailCnt(); //로그인 실패 횟수 증가
	}

	/**
	 * 로그인 성공 후 처리
	 * @param userIdx 회원 idx
	 */
	@Transactional
	public void handleSuccessLogin(@NotNull @Min(1) Integer userIdx) {
		Users user = usersRepository.findByIdxAndDelYn(userIdx, false)
				.orElseThrow(() -> new UsernameNotFoundException("Failed to find user by user idx."));

		user.resetLoginFailCnt(); //로그인 실패 횟수 초기화
		user.setLastLoginDt(LocalDateTime.now());
	}

	/**
	 * 회원 식별 값으로 회원 정보 조회
	 * @param userIdx 회원 식별 값
	 * @return 회원 정보가 담긴 dto 객체
	 */
	public UserDto getUser(@NotNull @Min(1) Integer userIdx) {
		Users user = usersRepository.findByIdxAndDelYn(userIdx, false)
				.orElseThrow(() -> new UsernameNotFoundException("Failed to find user by user idx."));
		
		return UserDto.entityConvertToDto(user);
	}

}
