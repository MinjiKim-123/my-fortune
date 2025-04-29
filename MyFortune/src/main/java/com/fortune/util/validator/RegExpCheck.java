package com.fortune.util.validator;

import com.fortune.util.RegExpPatterns;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 정규식 검사용 어노테이션
 */
@Constraint(validatedBy = RegExpValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegExpCheck {

	String message() default "입력 값을 확인해주세요.";
	
	RegExpPatterns.Type regExpType();

	Class<?>[] groups() default {};  
	Class<? extends Payload>[] payload() default {};  
}
