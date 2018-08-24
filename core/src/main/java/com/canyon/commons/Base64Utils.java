package com.canyon.commons;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * Base64编码工具类
 * 
 * @author SuYang
 *
 */
public class Base64Utils {

	private static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	/**
	 * 将Base64字符串转换为指定的字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String fromBase64(String s) {
		return fromBase64(s, DEFAULT_CHARSET);
	}

	/**
	 * 将Base64字符串转换为指定的字符串
	 * 
	 * @param s
	 * @param charset
	 * @return
	 */
	public static String fromBase64(String s, Charset charset) {
		if (StringUtils.isEmpty(s))
			return "";
		return new String(fromBase64ByteArray(s.getBytes(charset)), charset);
	}
	
	/**
	 * 将Base64二进制数组转换为指定的二进制数组
	 * @param byteArrays
	 * @return
	 */
	public static byte[] fromBase64ByteArray(byte[] byteArrays){
		if(byteArrays == null)
			return null;
		if(byteArrays.length == 0)
			return new byte[] {};
		Base64 base64 = new Base64();
		return base64.decode(byteArrays);
	}

	/**
	 * 将指定的字符串转为Base64字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String toBase64(String s) {
		return toBase64(s, DEFAULT_CHARSET);
	}

	/**
	 * 将指定的字符串转为Base64字符串
	 * 
	 * @param s
	 * @param charset
	 * @return
	 */
	public static String toBase64(String s, Charset charset) {
		if (StringUtils.isEmpty(s))
			return "";
		return toBase64(s.getBytes(charset), charset);
	}

	/**
	 * 将指定的字符串转为Base64字符串
	 * @param data
	 * @return
	 */
	public static String toBase64(byte[] data) {
		if (data == null)
			return "";
		return toBase64(data, DEFAULT_CHARSET);
	}

	/**
	 * 将指定的字符串转为Base64字符串
	 * 
	 * @param data
	 * @param charset
	 * @return
	 */
	public static String toBase64(byte[] data, Charset charset) {
		if (data == null)
			return "";
		return new String(toBase64ByteArray(data), charset);
	}
	
	/**
	 * 将指定的二进制数组转为Base64二进制数组
	 * @param byteArrays
	 * @return
	 */
	public static byte[] toBase64ByteArray(byte[] byteArrays) {
		if(byteArrays == null)
			return null;
		if(byteArrays.length == 0)
			return new byte[] {};
		Base64 base64 = new Base64();
		return base64.encode(byteArrays);
	}
}
