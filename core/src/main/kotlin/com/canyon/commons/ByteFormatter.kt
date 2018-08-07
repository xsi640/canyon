package com.canyon.commons

/**
 * 字节大小显示方式
 * @author Yang
 *
 */
object ByteFormatter {

    private val KB = 1024.0f
    private val MB = ByteFormatter.KB * 1024.0f
    private val GB = ByteFormatter.MB * 1024.0f
    private val TB = ByteFormatter.GB * 1024.0f
    private val PB = ByteFormatter.TB * 1024.0f

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
        return if (size < ByteFormatter.KB) {
            String.format(ByteFormatter.BFormatPattern, size)
        } else if (size >= ByteFormatter.KB && size < ByteFormatter.MB) {
            String.format(ByteFormatter.KBFormatPattern, size / ByteFormatter.KB)
        } else if (size >= ByteFormatter.MB && size < ByteFormatter.GB) {
            String.format(ByteFormatter.MBFormatPattern, size / ByteFormatter.MB)
        } else if (size >= ByteFormatter.GB && size < ByteFormatter.TB) {
            String.format(ByteFormatter.GBFormatPattern, size / ByteFormatter.GB)
        } else if (size >= ByteFormatter.TB && size < ByteFormatter.PB) {
            String.format(ByteFormatter.PBFormatPattern, size / ByteFormatter.PB)
        } else {
            String.format(ByteFormatter.PBFormatPattern, size / ByteFormatter.PB)
        }
    }

    /**
     * 根据大小格式，将字节大小转换成字符串格式(12B,12KB,12GB,12TB,12PB)
     * @param size
     * @param type
     * @return
     */
    fun toString(size: Long, type: ByteFormatter.ESizeType): String {
        return when (type) {
            ByteFormatter.ESizeType.B -> String.format(ByteFormatter.BFormatPattern, size)
            ByteFormatter.ESizeType.KB -> String.format(ByteFormatter.KBFormatPattern, size / ByteFormatter.KB)
            ByteFormatter.ESizeType.MB -> String.format(ByteFormatter.MBFormatPattern, size / ByteFormatter.MB)
            ByteFormatter.ESizeType.GB -> String.format(ByteFormatter.GBFormatPattern, size / ByteFormatter.GB)
            ByteFormatter.ESizeType.TB -> String.format(ByteFormatter.TBFormatPattern, size / ByteFormatter.TB)
            ByteFormatter.ESizeType.PB -> String.format(ByteFormatter.PBFormatPattern, size / ByteFormatter.PB)
        }
    }

    /**
     * 根据字节大小获取大小的枚举（KB,MB,GB,TB,PB）
     * @param size
     * @return
     */
    fun getSizeType(size: Long): ByteFormatter.ESizeType {
        return if (size < ByteFormatter.KB) {
            ByteFormatter.ESizeType.B
        } else if (size >= ByteFormatter.KB && size < ByteFormatter.MB) {
            ByteFormatter.ESizeType.KB
        } else if (size >= ByteFormatter.MB && size < ByteFormatter.GB) {
            ByteFormatter.ESizeType.MB
        } else if (size >= ByteFormatter.GB && size < ByteFormatter.TB) {
            ByteFormatter.ESizeType.GB
        } else if (size >= ByteFormatter.TB && size < ByteFormatter.PB) {
            ByteFormatter.ESizeType.PB
        } else {
            ByteFormatter.ESizeType.PB
        }
    }

    enum class ESizeType {
        B, KB, MB, GB, TB, PB
    }
}
