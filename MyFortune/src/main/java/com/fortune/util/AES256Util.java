package com.fortune.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

/**
 * aes256 암복호화 유틸리티
 */
@Slf4j
@Component
public class AES256Util {
	
	private final SecretKey secretKey;
	
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	
	public AES256Util(PropertyUtil propertyUtil){
		String aes256Key = propertyUtil.getProperty("key.aes256");
		if(aes256Key == null || aes256Key.isBlank())
			throw new IllegalArgumentException("Failed to get aes256 key.");
		
		this.secretKey = new SecretKeySpec(aes256Key.getBytes(),"AES");
	}

	/**
	 * 암호화
	 * @param data 암호화할 문자
	 * @return 암호화된 문자
	 */
	public String encrypt(String data) throws GeneralSecurityException{
		try {
			// 랜덤 IV 생성
			byte[] iv = new byte[16];
			new SecureRandom().nextBytes(iv);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

			byte[] encrypted = cipher.doFinal(data.getBytes());

			String ivBase64 = Base64.getEncoder().encodeToString(iv);
			String encryptedBase64 = Base64.getEncoder().encodeToString(encrypted);

			return encryptedBase64 + ":" + ivBase64; // IV와 암호문을 함께 저장
		} catch (GeneralSecurityException e){
			log.error("Encrypt failed.", e);
			throw e;
		}
	}

	/**
	 * 복호화 
	 * @param encryptData 암호화된 문자
	 * @return 복호화된 문자
	 */
	public String decrypt(String encryptData) throws GeneralSecurityException {
		try{
			String[] values = encryptData.split(":");
			if(values.length != 2)
				throw new IllegalArgumentException("Invalid encrypted data format.");
			
			String iv = values[1];
			IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.getDecoder().decode(iv.getBytes()));

			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
			
			String data = values[0];
			byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(data.getBytes()));

			return new String(decrypted);
		} catch (GeneralSecurityException e) {
			log.error("Decrypt failed.", e);
			throw e;
		}
	}
}
