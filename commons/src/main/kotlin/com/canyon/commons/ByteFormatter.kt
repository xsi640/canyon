package com.canyon.commons

/**
 * 字节大小显示方式
 * @author Yang
 *
 */
object ByteFormatter {

    private val KB = 1024.0f
    private val MB = KB * 1024.0f
    private val GB = MB * 1024.0f
    private val TB = GB * 1024.0f
    private val PB = TB * 1024.0f

    private const val BFormatPattern = "%s B"
    private const val KBFormatPattern = "%.2f KB"
    private const val MBFormatPattern = "%.2f MB"
    private const val GBFormatPattern = "%.2f GB"
    private const val TBFormatPattern = "%.2f TB"
    private const val PBFormatPattern = "%.2f PB"

    /**
     * 将字节大小转换成字符串格式(12B,12KB,12GB,12TB,12PB)
     * @param size
     * @return
     */
    fun toString(size: Long): String {
        return if (size < KB) {
            String.format(BFormatPattern, size)
        } else if (size >= KB && size < MB) {
            String.format(KBFormatPattern, size / KB)
        } else if (size >= MB && size < GB) {
            String.format(MBFormatPattern, size / MB)
        } else if (size >= GB && size < TB) {
            String.format(GBFormatPattern, size / GB)
        } else if (size >= TB && size < PB) {
            String.format(PBFormatPattern, size / PB)
        } else {
            String.format(PBFormatPattern, size / PB)
        }
    }

    /**
     * 根据大小格式，将字节大小转换成字符串格式(12B,12KB,12GB,12TB,12PB)
     * @param size
     * @param type
     * @return
     */
    fun toString(size: Long, type: ESizeType): String {
        return when (type) {
            ESizeType.B -> String.format(BFormatPattern, size)
            ESizeType.KB -> String.format(KBFormatPattern, size / KB)
            ESizeType.MB -> String.format(MBFormatPattern, size / MB)
            ESizeType.GB -> String.format(GBFormatPattern, size / GB)
            ESizeType.TB -> String.format(TBFormatPattern, size / TB)
            ESizeType.PB -> String.format(PBFormatPattern, size / PB)
        }
    }

    /**
     * 根据字节大小获取大小的枚举（KB,MB,GB,TB,PB）
     * @param size
     * @return
     */
    fun getSizeType(size: Long): ESizeType {
        return if (size < KB) {
            ESizeType.B
        } else if (size >= KB && size < MB) {
            ESizeType.KB
        } else if (size >= MB && size < GB) {
            ESizeType.MB
        } else if (size >= GB && size < TB) {
            ESizeType.GB
        } else if (size >= TB && size < PB) {
            ESizeType.PB
        } else {
            ESizeType.PB
        }
    }

    enum class ESizeType {
        B, KB, MB, GB, TB, PB
    }
}
