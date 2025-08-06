package com.fortune.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 운세 dto
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FortuneDto {
	
	/**
	 * 사용자명
	 */
	private String name;

	/**
	 * 운세 내용
	 */
	private String fortune;
}
