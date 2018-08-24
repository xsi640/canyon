package com.canyon.commons;

import java.util.Date;
import java.util.UUID;

public class TypeParse {

	public static Integer toInt(String str, Integer defaultVal) {
		Integer result = defaultVal;
		try {
			result = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Integer toInt(String str) {
		return toInt(str, 0);
	}

	public static Integer toInt(Object obj) {
		return toInt(obj, 0);
	}

	public static Integer toInt(Object obj, Integer defaultVal) {
		return toInt(obj.toString(), defaultVal);
	}

	public static Long toLong(String str, Long defaultVal) {
		Long result = defaultVal;
		try {
			result = Long.parseLong(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Long toLong(String str) {
		return toLong(str, 0l);
	}

	public static Long toLong(Object obj) {
		return toLong(obj, 0l);
	}

	public static Long toLong(Object obj, Long defaultVal) {
		return toLong(obj.toString(), defaultVal);
	}

	public static Boolean toBoolean(String str, Boolean defaultVal) {
		Boolean result = defaultVal;
		try {
			result = Boolean.parseBoolean(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Boolean toBoolean(String str) {
		return toBoolean(str, false);
	}

	public static Boolean toBoolean(Object obj) {
		return toBoolean(obj, false);
	}

	public static Boolean toBoolean(Object obj, Boolean defaultVal) {
		return toBoolean(obj.toString(), defaultVal);
	}

	public static Float toFloat(String str, Float defaultVal) {
		Float result = defaultVal;
		try {
			result = Float.parseFloat(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Float toFloat(String str) {
		return toFloat(str, 0f);
	}

	public static Float toFloat(Object obj) {
		return toFloat(obj, 0f);
	}

	public static Float toFloat(Object obj, Float defaultVal) {
		return toFloat(obj.toString(), defaultVal);
	}

	public static Double toDouble(String str, Double defaultVal) {
		Double result = defaultVal;
		try {
			result = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Double toDouble(String str) {
		return toDouble(str, 0d);
	}

	public static Double toDouble(Object obj, Double defaultVal) {
		return toDouble(obj.toString(), defaultVal);
	}

	public static Double toDouble(Object obj) {
		return toDouble(obj, 0d);
	}

	public static Short toShort(String str, Short defaultVal) {
		Short result = defaultVal;
		try {
			result = Short.parseShort(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Short toShort(String str) {
		return toShort(str, (short) 0);
	}

	public static Short toShort(Object obj, Short defaultVal) {
		return toShort(obj.toString(), defaultVal);
	}

	public static Short toShort(Object obj) {
		return toShort(obj, (short) 0);
	}

	public static Byte toByte(String str, Byte defaultVal) {
		Byte result = defaultVal;
		try {
			result = Byte.parseByte(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Byte toByte(String str) {
		return toByte(str, (byte) 0);
	}

	public static Byte toByte(Object obj, Byte defaultVal) {
		return toByte(obj.toString(), defaultVal);
	}

	public static Byte toByte(Object obj) {
		return toByte(obj, (byte) 0);
	}

	public static UUID toUUID(String str, UUID defaultVal) {
		UUID result = defaultVal;
		try {
			result = UUIDUtils.fromString(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static UUID toUUID(String str) {
		return toUUID(str, UUIDUtils.UUID_EMPTY);
	}

	public static UUID toUUID(Object obj, UUID defaultVal) {
		return toUUID(obj.toString(), defaultVal);
	}

	public static UUID toUUID(Object obj) {
		return toUUID(obj, UUIDUtils.UUID_EMPTY);
	}

	public static Date toDate(String str, Date defaultVal, String pattern) {
		return DateUtils.formString(str, defaultVal, pattern);
	}

	public static Date toDate(String str, Date defaultVal) {
		return toDate(str, defaultVal, null);
	}

	public static Date toDate(String str) {
		return toDate(str, DateUtils.Date_EMPTY);
	}

	public static Date toDate(String str, String pattern){
		return DateUtils.formString(str, DateUtils.Date_EMPTY, pattern);
	}

	public static Date toDate(Object obj, Date defaultVal, String pattern) {
		return toDate(obj.toString(), defaultVal, pattern);
	}

	public static Date toDate(Object obj, Date defaultVal) {
		return toDate(obj, defaultVal);
	}

	public static Date toDate(Object obj) {
		return toDate(obj, DateUtils.Date_EMPTY);
	}
}
