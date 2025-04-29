package com.fortune.entity.code;

import com.fortune.entity.code.converter.AbstractEnumConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserGenderCode implements EnumField<String>{
	WOMAN("W", "여자"),MAN("M", "남자");
	
	private final String data;
	
	private final String desc;
	
	public static class UserGenderCodeConverter extends AbstractEnumConverter<UserGenderCode, String>{
		public UserGenderCodeConverter() {
			super(UserGenderCode.class);
		}
	}
}
