package com.canyon.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	private static final Integer FLAGS = Pattern.CASE_INSENSITIVE | Pattern.MULTILINE;

	public static final String HTMLCOLOR = "^#?([a-f]|[A-F]|[0-9]){3}(([a-f]|[A-F]|[0-9]){3})?$";
	public static final String NUMBER = "^[0-9]+$";
	public static final String ENGLISH = "^[A-Za-z0-9]+$";
	public static final String EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
	public static final String IP = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$";
	public static final String URL = "^(http|https|ftp|rtsp|mms):(\\/\\/|\\\\\\\\)[A-Za-z0-9%\\-_@]+\\.[A-Za-z0-9%\\-_@]+[A-Za-z0-9\\.\\/=\\?%\\-&_~`@:\\+!;]*$";
	public static final String CHINESE = "^[\\u4e00-\\u9fa5]{2,}$";
	public static final String URLPORT = "^(.*)://([0-9,a-z,A-Z,-,.]+):([0-9]+)?";
	public static final String DOMAIN = "([0-9A-Za-z]{2,}\\.[0-9A-Za-z]{2,3}|[0-9A-Za-z]{2,}\\.[0-9A-Za-z]{2,3})$";

	public static final Pattern HTMLCOLOR_PATTERN = Pattern.compile(HTMLCOLOR, FLAGS);
	public static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER, FLAGS);
	public static final Pattern ENGLISH_PATTERN = Pattern.compile(ENGLISH, FLAGS);
	public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL, FLAGS);
	public static final Pattern IP_PATTERN = Pattern.compile(IP, FLAGS);
	public static final Pattern URL_PATTERN = Pattern.compile(URL, FLAGS);
	public static final Pattern CHINESE_PATTERN = Pattern.compile(CHINESE, FLAGS);
	public static final Pattern URLPORT_PATTERN = Pattern.compile(URLPORT, FLAGS);
	public static final Pattern DOMAIN_PATTERN = Pattern.compile(DOMAIN, FLAGS);

	public static boolean isHtmlColor(String str) {
		if (str == null || str.isEmpty())
			return false;

		Matcher m = HTMLCOLOR_PATTERN.matcher(str);
		return m.find();
	}

	public static boolean isNumber(String str) {
		if (str == null || str.isEmpty())
			return false;

		Matcher m = NUMBER_PATTERN.matcher(str);
		return m.find();
	}

	public static boolean isEnglish(String str) {
		if (str == null || str.isEmpty())
			return false;

		Matcher m = ENGLISH_PATTERN.matcher(str);
		return m.find();
	}

	public static boolean isEmail(String str) {
		if (str == null || str.isEmpty())
			return false;

		Matcher m = EMAIL_PATTERN.matcher(str);
		return m.find();
	}

	public static boolean isIP(String str) {
		if (str == null || str.isEmpty())
			return false;

		Matcher m = IP_PATTERN.matcher(str);
		return m.find();
	}

	public static boolean isUrl(String str) {
		if (str == null || str.isEmpty())
			return false;

		Matcher m = URL_PATTERN.matcher(str);
		return m.find();
	}

	public static boolean isChinese(String str) {
		if (str == null || str.isEmpty())
			return false;

		Matcher m = CHINESE_PATTERN.matcher(str);
		return m.find();
	}

	public static boolean isDomain(String str){
		if(str == null || str.isEmpty())
			return false;
		
		Matcher m = DOMAIN_PATTERN.matcher(str);
		return m.find();
	}
	
	public static void getUrlProctocol(String url, Ref<String> proc, Ref<String> ipAddress, Ref<String> port) {
		proc.setValue("");
		ipAddress.setValue("");
		port.setValue("");

		Matcher m = URLPORT_PATTERN.matcher(url);
		if (m.find()) {
			proc.setValue(m.group(1).toString());
			ipAddress.setValue(m.group(2).toString());
			port.setValue(m.group(3).toString());
		}
	}
}
