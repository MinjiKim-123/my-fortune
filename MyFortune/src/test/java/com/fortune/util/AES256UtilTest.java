package com.fortune.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AES256UtilTest {
	
	@Mock
	PropertyUtil propertyUtil;
	
	@BeforeEach
	void setProperty(){
		String mockAes256Key = "12345678901234567890123456789012"; 
		when(propertyUtil.getProperty("key.aes256")).thenReturn(mockAes256Key);
	}
	
	@Test
	@DisplayName("암복호화 성공 테스트")
	void testEncryptAndDecrypt_success() throws GeneralSecurityException {
		//given
		AES256Util aes256Util = new AES256Util(propertyUtil);
		String value = "test@gmail.com";

		//when
		String encrypted = aes256Util.encrypt(value);
		String decrypted = aes256Util.decrypt(encrypted);

		//then
		assertEquals(value, decrypted);
	}
}
