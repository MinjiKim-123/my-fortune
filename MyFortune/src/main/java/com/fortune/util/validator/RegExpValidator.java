package com.fortune.util.validator;

import com.fortune.util.RegExpPatterns;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 정규식 검증용 Custom validator
 */
public class RegExpValidator implements ConstraintValidator<RegExpCheck, String> {
	
	private RegExpPatterns.Type regExpType;
	
	@Override
	public void initialize(RegExpCheck constraintAnnotation) {
		this.regExpType = constraintAnnotation.regExpType();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid = (value != null && this.regExpType.getPattern().matcher(value).matches());
		
		if(!isValid){ //검증 실패시 메세지 등록
			context.disableDefaultConstraintViolation(); //기본 메세지 disable 처리
			context.buildConstraintViolationWithTemplate(this.regExpType.getErrorMessage())
					.addConstraintViolation();
		}
			
		return isValid;
	}
}
