package com.fortune.service;

import com.fortune.dto.users.PrincipalUserDetails;
import com.fortune.dto.users.RegistUserDto;
import com.fortune.dto.users.UserDto;
import com.fortune.entity.Users;
import com.fortune.repository.UsersRepository;
import com.fortune.util.RegExpUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

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
				.user(UserDto.entityConvertToDto(user))
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
	public int registUser(@Valid RegistUserDto userDto) {
		if (!RegExpUtil.isValidId(userDto.getUserId()))
			throw new IllegalArgumentException("Failed by invalid user id. " + userDto.getUserId());
		else if (!RegExpUtil.isValidPwd(userDto.getPwd()))
			throw new IllegalArgumentException("Failed by invalid user password.");
		else if (userDto.getNickname().isEmpty() || userDto.getNickname().length() > 15)
			throw new IllegalArgumentException("Failed by invalid user nickname." + userDto.getNickname());

		Users newUser = usersRepository.save(Users.builder()
				.userId(userDto.getUserId())
				.nickname(userDto.getNickname())
				.pwd(bCryptPasswordEncoder.encode(userDto.getPwd()))
				.build());

		return newUser.getIdx();
	}

	/**
	 * 비밀번호 오류 처리
	 * @param userIdx 회원 idx
	 */
	@Transactional
	public void handleWrongPwdAttempt(Integer userIdx) {
		Users user = usersRepository.findByIdxAndDelYn(userIdx, false)
				.orElseThrow(() -> new UsernameNotFoundException("Failed to find user by user idx."));

		user.incLoginFailCnt(); //로그인 실패 횟수 증가
	}

	/**
	 * 로그인 성공 후 처리
	 * @param userIdx 회원 idx
	 */
	@Transactional
	public void handleSuccessLogin(Integer userIdx) {
		Users user = usersRepository.findByIdxAndDelYn(userIdx, false)
				.orElseThrow(() -> new UsernameNotFoundException("Failed to find user by user idx."));

		user.resetLoginFailCnt(); //로그인 실패 횟수 초기화
	}

}
