package com.fortune.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class AjaxResultVo<T>{
	private String resultCode;
	private String resultMessage;
	private T data;
}