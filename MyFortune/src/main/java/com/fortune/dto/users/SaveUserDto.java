package com.fortune.dto.users;

import com.fortune.entity.code.UserGenderCode;
import com.fortune.util.RegExpPatterns;
import com.fortune.util.validator.RegExpCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

// TODO validation어노테이션에 insert/update에 맞춰서 그룹 속성 설정 추가
/**
 * 회원 등록용 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserDto {

	@NotBlank(message = "아이디를 입력해주세요.")
	@RegExpCheck(regExpType = RegExpPatterns.Type.REG_ID)
	private String userId;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@RegExpCheck(regExpType = RegExpPatterns.Type.REG_PWD)
	private String pwd;

	@NotBlank(message = "이름을 입력해주세요.")
	@Length(max = 10, message = "이름은 최대 10자까지 입력해주세요.")
	private String name;

	@NotBlank(message = "연락처를 입력해주세요.")
	@RegExpCheck(regExpType = RegExpPatterns.Type.REG_PHONE)
	private String phone;

	@NotBlank(message = "이메일을 입력해주세요.")
	@Length(max = 30)
	@RegExpCheck(regExpType = RegExpPatterns.Type.REG_EMAIL)
	private String email;
	
	@NotNull(message = "생년월일 및 태어난 시각을 입력해주세요.")
	private LocalDateTime birthDt;

	@NotNull(message = "성별을 입력해주세요.")
	private UserGenderCode gender;
	
	@NotNull(message = "양력/음력 여부를 선택해주세요.")
	private String solarYn;
}
