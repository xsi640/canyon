package com.canyon.commons;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EncryptUtils {
	private static String strKey = "c8cdf47595f94959850d49a703682e8f";	
	private static Cipher ecipher;
	private static Cipher dcipher;

	static {
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(strKey.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(dks);
			ecipher = Cipher.getInstance("DES");
			dcipher = Cipher.getInstance("DES");
			ecipher.init(Cipher.ENCRYPT_MODE, key, sr);
			dcipher.init(Cipher.DECRYPT_MODE, key, sr);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}

	public static byte[] encryptToByteArray(byte[] data) {
		try {
			return ecipher.doFinal(data);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decryptFromByteArray(byte[] data) {
		try {
			return dcipher.doFinal(data);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encrypt(String text) {
		String result = "";
		byte[] data = EncodingUtils.decode(text, Charset.forName("UTF-8"));
		if (data != null && data.length > 0) {
			data = encryptToByteArray(data);
			result = EncodingUtils.base64Encode(data);
		}
		return result;
	}

	public static String decrypt(String text) {
		String result = "";
		byte[] data = EncodingUtils.base64Decode(text);
		if (data != null && data.length > 0) {
			data = decryptFromByteArray(data);
			result = EncodingUtils.encode(data, Charset.forName("UTF-8"));
		}
		return result;
	}
}
