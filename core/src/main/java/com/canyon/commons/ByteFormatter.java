package com.canyon.commons;

/**
 * 字节大小显示方式
 * @author Yang
 *
 */
public class ByteFormatter {

	private final static float KB = 1024.0f;
	private final static float MB = KB * 1024.0f;
	private final static float GB = MB * 1024.0f;
	private final static float TB = GB * 1024.0f;
	private final static float PB = TB * 1024.0f;

	private final static String BFormatPattern = "%s B";
	private final static String KBFormatPattern = "%.2f KB";
	private final static String MBFormatPattern = "%.2f MB";
	private final static String GBFormatPattern = "%.2f GB";
	private final static String TBFormatPattern = "%.2f TB";
	private final static String PBFormatPattern = "%.2f PB";

	/**
	 * 将字节大小转换成字符串格式(12B,12KB,12GB,12TB,12PB)
	 * @param size
	 * @return
	 */
	public static String toString(long size) {
		String result = "";
		if (size < KB) {
			result = String.format(BFormatPattern, size);
		} else if (size >= KB && size < MB) {
			result = String.format(KBFormatPattern, size / KB);
		} else if (size >= MB && size < GB) {
			result = String.format(MBFormatPattern, size / MB);
		} else if (size >= GB && size < TB) {
			result = String.format(GBFormatPattern, size / GB);
		} else if (size >= TB && size < PB) {
			result = String.format(PBFormatPattern, size / PB);
		} else {
			result = String.format(PBFormatPattern, size / PB);
		}
		return result;
	}

	/**
	 * 根据大小格式，将字节大小转换成字符串格式(12B,12KB,12GB,12TB,12PB)
	 * @param size
	 * @param type
	 * @return
	 */
	public static String toString(long size, ESizeType type) {
		String result = "";
		switch (type) {
		case B:
			result = String.format(BFormatPattern, size);
			break;
		case KB:
			result = String.format(KBFormatPattern, size / KB);
			break;
		case MB:
			result = String.format(MBFormatPattern, size / MB);
			break;
		case GB:
			result = String.format(GBFormatPattern, size / GB);
			break;
		case TB:
			result = String.format(TBFormatPattern, size / TB);
			break;
		case PB:
			result = String.format(PBFormatPattern, size / PB);
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * 根据字节大小获取大小的枚举（KB,MB,GB,TB,PB）
	 * @param size
	 * @return
	 */
	public static ESizeType getSizeType(long size) {
		ESizeType result = ESizeType.KB;
		if (size < KB) {
			result = ESizeType.B;
		} else if (size >= KB && size < MB) {
			result = ESizeType.KB;
		} else if (size >= MB && size < GB) {
			result = ESizeType.MB;
		} else if (size >= GB && size < TB) {
			result = ESizeType.GB;
		} else if (size >= TB && size < PB) {
			result = ESizeType.PB;
		} else {
			result = ESizeType.PB;
		}
		return result;
	}

	public enum ESizeType {
		B, KB, MB, GB, TB, PB
	}
}
