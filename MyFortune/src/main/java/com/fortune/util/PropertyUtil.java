package com.fortune.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PropertyUtil {

	private final Environment environment;
	
	public String getProperty(String key){
		return environment.getProperty(key);
	}
}
