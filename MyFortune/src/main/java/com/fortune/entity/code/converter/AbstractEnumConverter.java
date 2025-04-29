package com.fortune.entity.code.converter;

import com.fortune.entity.code.EnumField;
import com.fortune.util.EnumDataConvertUtil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * AttributeConverter를 공통으로 사용하기 위한 추상 클래스
 * @param <T> EnumField 인터페이스를 구현한 enum클래스
 * @param <V> enum 클래스에서 사용하는 데이터 타입
 */
@Converter(autoApply = true)
public abstract class AbstractEnumConverter<T extends Enum<T> & EnumField<V>, V> implements AttributeConverter<T, V> {

	private final Class<T> targetEnumClass;
	
	public AbstractEnumConverter(Class<T> targetEnumClass) {
		this.targetEnumClass = targetEnumClass;
	}

	/**
	 * enum코드를 db저장 형식으로 변환
	 * @param code
	 * @return db에 저장할 데이터
	 */
	@Override
	public V convertToDatabaseColumn(T code) {
		if(code == null)
			throw new IllegalArgumentException("Enum code is null.");
		
		return code.getData();
	}

	/**
	 * dbData를 enum 코드로 변환
	 * @param dbData
	 * @return enum코드
	 */
	@Override
	public T convertToEntityAttribute(V dbData) {
		if(dbData == null)
			throw new IllegalArgumentException("DbData is null.");
		
		return EnumDataConvertUtil.getEnumFromData(dbData, targetEnumClass);
	}

}
