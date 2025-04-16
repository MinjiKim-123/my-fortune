package com.fortune.dto.users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistUserDto {

	@NotBlank(message = "아이디를 입력해주세요.")
	private String userId;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	private String pwd;

	@NotBlank(message = "닉네임를 입력해주세요.")
	private String nickname;
}
