package com.fortune.dto.users;

import java.time.LocalDateTime;

import com.fortune.entity.Users;
import com.fortune.entity.code.UserGenderCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private int idx;

	private String userId;

	private String pwd;
	
	private String email;
			
	private String phone;

	private UserGenderCode gender;

	private LocalDateTime birthDt;

	private String name;

	private int loginFailCnt;

	private boolean isLocked;
	
	private LocalDateTime lastLoginDt;

	/**
	 * 회원 엔티티 dto로 변환
	 * @param user
	 * @return
	 */
	public static UserDto entityConvertToDto(Users user) {
		return UserDto.builder()
				.idx(user.getIdx())
				.userId(user.getUserId())
				.pwd(user.getPwd())
				.email(user.getEmail())
				.phone(user.getPhone())
				.gender(user.getGender())
				.name(user.getName())
				.loginFailCnt(user.getLoginFailCnt())
				.isLocked(user.getLockYn())
				.lastLoginDt(user.getLastLoginDt())
				.birthDt(user.getBirthDt())
				.build();
	}

	public void erasePwd(){
		this.pwd = null;
	}

}
