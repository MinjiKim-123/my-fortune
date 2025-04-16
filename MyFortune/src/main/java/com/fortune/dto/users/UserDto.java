package com.fortune.dto.users;

import com.fortune.entity.Users;
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

	private String nickname;

	private int loginFailCnt;

	private boolean isLocked;

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
				.nickname(user.getNickname())
				.loginFailCnt(user.getLoginFailCnt())
				.isLocked(user.getLockYn())
				.build();
	}

	public void erasePwd(){
		this.pwd = null;
	}

}
