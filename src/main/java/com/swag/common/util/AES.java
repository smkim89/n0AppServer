package com.swag.common.util;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AES {
	static Logger logger = LoggerFactory.getLogger(AES.class);

	public static String aesEncodeStr(String str, String key) throws Exception {
		byte[] textBytes = str.getBytes();
		byte[] keyByte = Hex.decodeHex(key.toCharArray());
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		SecretKeySpec newKey = new SecretKeySpec(keyByte, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, newKey);

		return Hex.encodeHexString(cipher.doFinal(textBytes));

	}

	public static String aesDecodeStr(String str, String key) {
		byte[] textBytes;
		try {
			textBytes = Hex.decodeHex(str.toCharArray());
			byte[] keyByte = Hex.decodeHex(key.toCharArray());
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec newKey = new SecretKeySpec(keyByte, "AES");
			cipher.init(Cipher.DECRYPT_MODE, newKey);
			return new String(cipher.doFinal(textBytes));
		} catch (Exception e) {
			logger.info("AES Decode error: {}", e.toString());
			return "";
		}

	}
	
	public static String aesEncode(String str, String key, byte[] ivBytes) throws Exception {
		String result;
		try {
			byte[] textBytes = str.getBytes("UTF-8");
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher;
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
			result = Base64.encodeBase64String(cipher.doFinal(textBytes));
		} catch(Exception e) {
			logger.error("AES Encodeing", e.getMessage());
			throw e;
		}
		return result;
	}

	public static String aesDecode(String str, String key, byte[] ivBytes) throws Exception {
		String result;
		try {
			byte[] textBytes = Base64.decodeBase64(str);
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
			result = new String(cipher.doFinal(textBytes), "UTF-8"); 
		} catch (Exception e) {
			logger.error("AES Decodeing", e.getMessage());
			throw e;
		}
		return result;
	}
}