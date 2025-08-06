package com.fortune.util;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

/**
 * redis 사용을 위한 util
 */
@Component
@RequiredArgsConstructor
@Validated
public class RedisUtil {

	private final RedisTemplate<String, String> redisTemplate;
	
	
	/**
	 * 데이터 저장
	 * @param key 
	 * @param value
	 */
	public void setData(@NotBlank(message = "key 값을 입력해주세요.") String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}
	
	/**
	 * 데이터 저장
	 * @param key 
	 * @param value
	 * @param timeout (초)
	 */
	public void setData(@NotBlank(message = "key 값을 입력해주세요.") String key, String value, long timeout) {
		redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}	
	
	/**
	 * 데이터 저장
	 * @param key 
	 * @param value
	 * @param duration 시간 간격
	 */
	public void setData(@NotBlank(message = "key 값을 입력해주세요.") String key, String value, Duration duration) {
		setData(key, value, duration.getSeconds());
	}
	
	public String getData(String key) {
		return key; 
	}
}
