package com.yum.yumyums.util;

import java.util.Base64;

// base64기반으로 쌍방향 암호화를 시키는 클래스 (비밀번호에선 사용X)
public class SecureUtil {
	private static final Base64Util base64Util = new Base64Util();

	/**
	 * 문자열을 암호화하는 메소드
	 * @param plaintext 암호화할 평문 문자열
	 * @return 암호화된 문자열
	 */
	public static String calcEncrypt(String plaintext) {
		return base64Util.base64Encode(plaintext);
	}

	/**
	 * 암호화된 문자열을 복호화하는 메소드
	 * @param ciphertext 복호화할 암호화된 문자열
	 * @return 복호화된 평문 문자열
	 */
	public static String calcDecrypt(String ciphertext) {
		return base64Util.base64Decode(ciphertext);
	}




	private static class Base64Util {
		/**
		 * Base64로 인코딩하는 메소드
		 * @param plaintext 인코딩할 평문 문자열
		 * @return Base64로 인코딩된 문자열
		 */
		private String base64Encode(String plaintext) {
			return Base64.getEncoder().encodeToString(plaintext.getBytes());
		}

		/**
		 * Base64로 디코딩하는 메소드
		 * @param base64text 디코딩할 Base64 문자열
		 * @return 디코딩된 평문 문자열
		 */
		private String base64Decode(String base64text) {
			byte[] decodedBytes = Base64.getDecoder().decode(base64text);
			return new String(decodedBytes);
		}
	}
}
