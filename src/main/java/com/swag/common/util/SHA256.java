package com.swag.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by user on 2015-07-13.
 */
public class SHA256 {

	/**
	 * Order Hash를 생성한다.
	 * 이는 Broser와 Socket의 정보를 연결 할 때 사용한다.
	 *
	 * @param plain 암호화 할 평문
	 * @return SHA-256 해시코드
	 */
	public static String encryption (String plain) {
		String sha256 = "";
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(plain.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for (byte data : byteData) {
				sb.append(Integer.toString((data & 0xff) + 0x100, 16).substring(1));
			}
			sha256 = sb.toString();
		} catch(NoSuchAlgorithmException e) {

		}

		return sha256;
	}
}
