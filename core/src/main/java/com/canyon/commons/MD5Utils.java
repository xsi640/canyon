package com.canyon.commons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MD5Utils {
	public static String getMD5fromFile(String path) {
		String result = "";
		if (IOUtils.isFile(path))
			return result;

		FileInputStream stream = null;
		try {
			stream = new FileInputStream(path);
			result = org.apache.commons.codec.digest.DigestUtils.md5Hex(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static String getMD5fromString(String text){
		return org.apache.commons.codec.digest.DigestUtils.md5Hex(text);
	}
}
