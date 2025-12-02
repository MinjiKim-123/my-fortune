package com.fortune.dto.common;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AjaxResultDto<T>{
	private String resultCode;
	private String resultMessage;
	private T data;

	public static <T> AjaxResultDto<T> success(T data) {
		return new AjaxResultDto<>(AjaxResultDto.successCode, "처리를 성공했습니다.", data);
	}

	public static <T> AjaxResultDto<T> failure() {
		return new AjaxResultDto<>(AjaxResultDto.failureCode, "처리를 실패했습니다.", null);
	}
	
	public static <T> AjaxResultDto<T> failure(String resultCode, String resultMessage) {
		if (!StringUtils.hasText(resultCode))
			resultCode = AjaxResultDto.failureCode;
		if (!StringUtils.hasText(resultMessage))
			resultMessage = "처리를 실패했습니다.";

		return new AjaxResultDto<>(resultCode, resultMessage, null);
	}

	public static String successCode = "00";
	public static String failureCode = "10";
}