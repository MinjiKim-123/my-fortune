package com.fortune.util;

import com.fortune.entity.code.EnumField;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.NoSuchElementException;

/**
 * enum 코드 및 data를 변환하는 유틸리티 클래스
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnumDataConvertUtil {

	/**
	 * data를 enum코드로 변환
	 * @param data
	 * @param enumClass
	 * @return
	 * @param <T>
	 * @param <V>
	 */
	public static <T extends Enum<T> & EnumField<V>, V> T getEnumFromData(V data, Class<T> enumClass){
		if(enumClass == null)
			throw new IllegalArgumentException("Enum class is null.");
		else if(data == null)
			throw new IllegalArgumentException("Data is null.");
		
		for (T enumConstant : enumClass.getEnumConstants()) {
			if (enumConstant.getData().equals(data))
				return enumConstant;
		}
		
		throw new NoSuchElementException("Failed to find enum code by data. Data value: " + data);
	}

	/**
	 * enum코드를 data로 변환
	 * @param enumCode
	 * @return
	 * @param <T>
	 * @param <V>
	 */
	public static <T extends Enum<T> & EnumField<V>, V> V getDataFromEnum(T enumCode){
		if(enumCode == null)
			throw new IllegalArgumentException("Enum code is null.");
		
		return enumCode.getData();
	}
}
