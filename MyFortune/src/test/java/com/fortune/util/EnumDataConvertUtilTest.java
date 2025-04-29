package com.fortune.util;

import com.fortune.entity.code.UserGenderCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class EnumDataConvertUtilTest {

	@Test
	@DisplayName("유저 성별 data를 enum코드로 변환 성공 테스트")
	void testGetGenderEnumFromData_success(){
		String womanData = "W";
		UserGenderCode code = EnumDataConvertUtil.getEnumFromData(womanData, UserGenderCode.class);
		assertEquals(UserGenderCode.WOMAN, code);
	}

	@Test
	@DisplayName("유저 성별 enum 코드에서 data 가져오기 성공 테스트")
	void testGetDataFromGenderEnum_success(){
		String data = EnumDataConvertUtil.getDataFromEnum(UserGenderCode.MAN);
		assertEquals(UserGenderCode.MAN.getData(), data);
	}

	@Test
	@DisplayName("유저 성별 data를 enum코드로 변환시 존재하지 않는 값 입력 테스트")
	void testGetGenderEnumFromData_failed_noSuchEnum(){
		String data = "X";
		NoSuchElementException exception = assertThrows(NoSuchElementException.class, 
				() -> EnumDataConvertUtil.getEnumFromData(data, UserGenderCode.class));
		log.info("오류메세지: {}", exception.getMessage());
	}
	
}
